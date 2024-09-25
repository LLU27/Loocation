import { useLocation, Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { BiMaleFemale } from 'react-icons/bi';
import { FaBaby } from 'react-icons/fa6';
import { BiAccessibility } from 'react-icons/bi';
import { useAuth } from '../components/AuthContext';
import axios from 'axios';
import ReactStars from 'react-rating-stars-component';

const BathroomDetail = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [error, setError] = useState('');
  const bathroom = location.state;
  const [bathroomId, setBathroomId] = useState(null);
  const [ratings, setRatings] = useState([]);

  useEffect(() => {
    const findBathroom = async () => {
      try {
        const addressResponse = await axios.get(`http://localhost:8080/api/address/lat/${bathroom.latitude}/long/${bathroom.longitude}`);

        if (addressResponse.status === 200) {
          const address = addressResponse.data;
          console.log(address);
          const bathroomResponse = await axios.get(`http://localhost:8080/api/bathroom/${bathroom.name}/address/${address.addressId}`);

          if (bathroomResponse.status === 200) {
            const foundBathroom = bathroomResponse.data;
            console.log(foundBathroom);
            setBathroomId(foundBathroom.bathroomId);
          } else {
            console.error('Bathroom not found for the given address.');
            setBathroomId(null);
          }
        } else {
          console.error('Address not found for the given latitude and longitude.');
          setBathroomId(null);
        }
      } catch (err) {
        setError(err.message);
        console.error('Error fetching data:', err);
      }
    };

    findBathroom();
  }, [bathroom.latitude, bathroom.longitude]);

  useEffect(() => {
    const fetchRatings = async () => {
      if (bathroomId) {
        try {
          const response = await axios.get(`http://localhost:8080/api/rating/bathroom/${bathroomId}`);
          setRatings(response.data);
        } catch (err) {
          setError(err.message);
          console.log(err);
        }
      }
    };

    fetchRatings();
  }, [bathroomId]);

  const handleAddBathroom = () => {
    navigate(`/add/bathroom/${bathroom.id}`, { state: bathroom });
  };

  return (
    <div className='flex flex-col items-center mt-8'>
      <Link to='/bathrooms' className='text-blue-500 hover:underline mb-6'>
        ← Back to Bathrooms
      </Link>
      <Link to='/map' className='text-blue-500 hover:underline mb-6'>
        ← Back to Map
      </Link>

      <div className='bg-white p-6 shadow-md rounded-lg max-w-md w-full'>
        {bathroom ? (
          <div>
            <h2 className='text-2xl font-bold text-gray-800 mb-4'>{bathroom.name}</h2>
            <div className='mb-4'>
              <p className='text-gray-600 my-2'>
                <span className='font-semibold text-lg'>Address: </span>
                {bathroom.street}
              </p>
              {bathroom.directions && (
                <p className='text-gray-600 my-2'>
                  <span className='font-semibold text-lg'>Directions: </span>
                  {bathroom.directions}
                </p>
              )}
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <BiMaleFemale />
                <span className='font-semibold text-lg'>Unisex: </span>
                {bathroom.unisex ? 'Yes' : 'No'}
              </p>
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <FaBaby />
                <span className='font-semibold text-lg'>Changing Station:</span>
                {bathroom.changing_table ? 'Yes' : 'No'}
              </p>
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <BiAccessibility />
                <span className='font-semibold text-lg'>Accessibility: </span>
                {bathroom.accessible ? 'Yes' : 'No'}
              </p>
            </div>

            <div className='flex justify-between'>
              <a
                href={`https://www.google.com/maps/dir/?api=1&destination=${bathroom.latitude},${bathroom.longitude}`}
                target='_blank'
                rel='noopener noreferrer'
                className='btn btn-primary text-white'
              >
                Get Directions
              </a>
              {user && (
                <button className='btn btn-accent text-white mb-4' onClick={handleAddBathroom}>
                  Add Review
                </button>
              )}
            </div>

            <div className='mt-4'>
              <h3 className='text-xl font-semibold'>Ratings:</h3>
              {ratings.length > 0 ? (
                <ul className='mt-2'>
                  {ratings.map(rating => (
                    <li key={rating.id} className='border-b py-2'>
                      <ReactStars count={5} value={rating.rating} edit={false} size={24} activeColor='#ffd700' />
                      <p className='text-gray-600'>{`Comment: ${rating.comment}`}</p>
                    </li>
                  ))}
                </ul>
              ) : (
                <p className='text-gray-600'>No ratings available for this bathroom.</p>
              )}
            </div>
          </div>
        ) : (
          <p className='text-lg text-gray-500'>Bathroom data not available.</p>
        )}
      </div>
    </div>
  );
};

export default BathroomDetail;
