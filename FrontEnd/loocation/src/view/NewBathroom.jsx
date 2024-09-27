import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../components/AuthContext';

const NewBathroom = () => {
  const navigate = useNavigate();
  const { userId } = useAuth();
  const [name, setName] = useState('');
  const [street, setStreet] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [zip, setZip] = useState('');

  const [accessibility, setAccessibility] = useState('false');
  const [changingStation, setChangingStation] = useState('false');
  const [unisex, setUnisex] = useState('false');

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async e => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      const geocodeResponse = await axios.get(`https://maps.googleapis.com/maps/api/geocode/json`, {
        params: {
          address: `${street}, ${city}, ${state}`,
          key: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
        },
      });
      const { lat, lng } = geocodeResponse.data.results[0].geometry.location;
      console.log('Lat:', lat, 'Long:', lng);
      const addressResponse = await axios.get(`http://localhost:8080/api/address/lat/${lat}/long/${lng}`);
      let address;
      if (addressResponse.status === 204) {
        const newAddressData = {
          street,
          city,
          state,
          latitude: lat,
          longitude: lng,
        };

        const addAddressResponse = await axios.post('http://localhost:8080/api/address/add', newAddressData);
        address = addAddressResponse.data;
      } else {
        address = addressResponse.data;
      }

      const bathroomResponse = await axios.get(`http://localhost:8080/api/bathroom/${name}/address/${address.addressId}`);
      let bathroomId;

      if (bathroomResponse.status === 204) {
        const bathroomDataToAdd = {
          name,
          accessibility: accessibility === 'true',
          changing_station: changingStation === 'true',
          unisex: unisex === 'true',
          address,
        };

        console.log('New Bathroom Data:', bathroomDataToAdd);

        const addBathroomResponse = await axios.post('http://localhost:8080/api/bathroom/add', bathroomDataToAdd);
        bathroomId = addBathroomResponse.data.bathroomId;
        setSuccess('Bathroom added successfully!');
        setTimeout(() => {
          setSuccess('');
        }, 2000);
        setTimeout(() => {
          navigate(`/user/${userId}/bathrooms`);
        }, 2000);
      } else {
        bathroomId = bathroomResponse.data.bathroomId;
        setError('Bathroom already exists.');
      }

      const userBathroomData = {
        userId,
        bathroomId,
      };

      const userBathroomResponse = await axios.post(
        `http://localhost:8080/api/userbathroom/add/bathroomId/${bathroomId}/userId/${userId}`,
        userBathroomData
      );
      console.log('User Bathroom Response:', userBathroomResponse.data);

      setName('');
      setStreet('');
      setCity('');
      setState('');
      setZip('');
      setAccessibility('false');
      setChangingStation('false');
      setUnisex('false');
    } catch (err) {
      setError('Failed to add bathroom. Please try again.');
      console.error(err);
    }
  };

  return (
    <div className='flex items-center justify-center min-h-screen bg-gray-100'>
      <div className='max-w-md w-full p-6 bg-white rounded-lg shadow-lg'>
        <h2 className='text-3xl font-bold mb-4 text-center text-gray-800'>Add New Bathroom</h2>
        {error && <div className='bg-red-500 text-white p-2 rounded mb-4'>{error}</div>}
        {success && <div className='bg-green-500 text-white p-2 rounded mb-4'>{success}</div>}
        <form onSubmit={handleSubmit} className='space-y-4'>
          <div>
            <label htmlFor='name' className='block text-gray-700 font-semibold'>
              Bathroom Name
            </label>
            <input
              type='text'
              id='name'
              value={name}
              onChange={e => setName(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded bg-white text-gray-700'
            />
          </div>
          <div>
            <label htmlFor='street' className='block text-gray-700 font-semibold'>
              Street
            </label>
            <input
              type='text'
              id='street'
              value={street}
              onChange={e => setStreet(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded bg-white text-gray-700'
            />
          </div>
          <div>
            <label htmlFor='city' className='block text-gray-700 font-semibold'>
              City
            </label>
            <input
              type='text'
              id='city'
              value={city}
              onChange={e => setCity(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded bg-white text-gray-700'
            />
          </div>
          <div>
            <label htmlFor='state' className='block text-gray-700 font-semibold'>
              State
            </label>
            <input
              type='text'
              id='state'
              value={state}
              onChange={e => setState(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded bg-white text-gray-700'
            />
          </div>
          <div>
            <label htmlFor='zip' className='block text-gray-700 font-semibold'>
              Zipcode
            </label>
            <input
              type='text'
              id='zip'
              value={zip}
              onChange={e => setZip(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded bg-white text-gray-700'
            />
          </div>
          <div>
            <label className='block text-gray-700 font-semibold'>Accessibility</label>
            <div className='flex gap-4 items-center'>
              <label className='text-gray-700 flex gap-2'>
                <input
                  className='radio radio-primary '
                  type='radio'
                  value='true'
                  checked={accessibility === 'true'}
                  onChange={() => setAccessibility('true')}
                />
                Yes
              </label>
              <label className='text-gray-700 flex gap-2'>
                <input
                  className='radio radio-primary '
                  type='radio'
                  value='false'
                  checked={accessibility === 'false'}
                  onChange={() => setAccessibility('false')}
                />
                No
              </label>
            </div>
          </div>
          <div>
            <label className='block text-gray-700 font-semibold'>Changing Station</label>
            <div className='flex gap-4 items-center'>
              <label className='text-gray-700 flex gap-2'>
                <input
                  className='radio radio-primary '
                  type='radio'
                  value='true'
                  checked={changingStation === 'true'}
                  onChange={() => setChangingStation('true')}
                />
                Yes
              </label>
              <label className='text-gray-700 flex gap-2'>
                <input
                  className='radio radio-primary '
                  type='radio'
                  value='false'
                  checked={changingStation === 'false'}
                  onChange={() => setChangingStation('false')}
                />
                No
              </label>
            </div>
          </div>
          <div>
            <label className='block text-gray-700 font-semibold'>Unisex</label>
            <div className='flex gap-4 items-center'>
              <label className='text-gray-700 flex gap-2'>
                <input className='radio radio-primary ' type='radio' value='true' checked={unisex === 'true'} onChange={() => setUnisex('true')} />
                Yes
              </label>
              <label className='text-gray-700 flex gap-2'>
                <input className='radio radio-primary ' type='radio' value='false' checked={unisex === 'false'} onChange={() => setUnisex('false')} />
                No
              </label>
            </div>
          </div>
          <button type='submit' className='btn btn-primary w-full p-2 rounded transition duration-200 text-white'>
            Add Bathroom
          </button>
        </form>
      </div>
    </div>
  );
};

export default NewBathroom;
