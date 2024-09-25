import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import ReactStars from 'react-rating-stars-component';
import { useAuth } from '../components/AuthContext';
import axios from 'axios';

const AddBathroom = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { userId } = useAuth();
  const { street, city, name, state, latitude, longitude, accessible, changing_table, unisex } = location.state;

  const [comments, setComments] = useState('');
  const [bathroomId, setBathroomId] = useState(null);
  const [rating, setRating] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');
  const [error, setError] = useState('');

  const onStarClick = nextValue => {
    setRating(nextValue);
  };

  useEffect(() => {
    const findOrCreateBathroom = async () => {
      try {
        const addressResponse = await axios.get(`http://localhost:8080/api/address/lat/${latitude}/long/${longitude}`);
        const addressData = addressResponse.status;
        let address;
        if (addressData === 204) {
          const newAddressData = {
            street,
            city,
            state,
            latitude,
            longitude,
          };
          console.log('new', newAddressData);

          const addAddressResponse = await axios.post('http://localhost:8080/api/address/add', newAddressData);
          address = addAddressResponse.data;
        } else {
          address = addressResponse.data;
        }
        const bathroomResponse = await axios.get(`http://localhost:8080/api/bathroom/${name}/address/${address.addressId}`);
        const bathroomData = bathroomResponse.status;
        let bathroomId;
        if (bathroomData === 204) {
          const bathroomDataToAdd = {
            name,
            accessible,
            changing_table,
            unisex,
            address,
          };
          const addBathroomResponse = await axios.post('http://localhost:8080/api/bathroom/add', bathroomDataToAdd);
          bathroomId = addBathroomResponse.data.bathroomId;
        } else {
          bathroomId = bathroomResponse.data.bathroomId;
        }
        setBathroomId(bathroomId);
      } catch (err) {
        setError(err.message);
        console.log(err);
      }
    };

    findOrCreateBathroom();
  }, [latitude, longitude]);

  const handleSubmit = async e => {
    e.preventDefault();

    const review = {
      bathroomId,
      userId,
      rating,
      comment: comments,
    };

    const userBathroom = {
      bathroomId,
      userId,
    };

    try {
      const ratingResponse = await axios.post('http://localhost:8080/api/rating/add', review);
      console.log('Rating submitted successfully', ratingResponse.data);
      const userBathroomResponse = await axios.post(
        `http://localhost:8080/api/userbathroom/add/bathroomId/${bathroomId}/userId/${userId}`,
        userBathroom
      );
      console.log('Rating submitted successfully', userBathroomResponse.data);

      setSuccessMessage('Rating added successfully!');
      setTimeout(() => {
        navigate(`/user/${userId}/bathrooms`);
      }, 2000);
    } catch (error) {
      console.error('Failed to submit rating', error);
      setError(error.response ? error.response.data.message : error.message);
    }
  };

  return (
    <div className='flex flex-col items-center mt-8'>
      <h2 className='text-2xl font-bold mb-4'>Add Bathroom Review</h2>
      <div className='mb-4'>
        <label className='block text-lg font-bold mb-2 w-full'>Bathroom Name</label>
        <p className='py-2 px-3 bg-gray-100 mb-3 rounded text-gray-700'>{name || 'N/A'}</p>
        <label className='block text-lg font-bold mb-2 w-full'>Address</label>
        <p className='py-2 px-3 mb-3 bg-gray-100 rounded text-gray-700'>
          {street || 'N/A'},{city || 'N/A'}
        </p>
      </div>
      <form onSubmit={handleSubmit} className='bg-white p-6 shadow-md rounded-lg max-w-md w-full'>
        <div className='mb-4 '>
          <label className='block text-gray-700 text-lg font-bold mb-2'>Comments</label>
          <textarea
            value={comments}
            onChange={e => setComments(e.target.value)}
            className='border rounded w-full py-2 px-3 text-gray-700 mb-3 text-white'
            placeholder='Enter your comments'
          ></textarea>
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700 text-lg font-bold mb-2'>Rating</label>
          <div className='flex gap-2'>
            <ReactStars count={5} onChange={onStarClick} size={24} activeColor='#ffd700' />
          </div>
        </div>
        <button type='submit' className='btn btn-primary text-white mt-4'>
          Submit
        </button>
      </form>
      {successMessage && <div className='alert alert-success m-4'>{successMessage}</div>}
    </div>
  );
};

export default AddBathroom;
