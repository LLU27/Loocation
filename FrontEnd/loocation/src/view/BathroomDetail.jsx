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
  const { user, userId } = useAuth();
  const [error, setError] = useState('');
  const bathroom = location.state;
  const [bathroomId, setBathroomId] = useState(null);
  const [ratings, setRatings] = useState([]);
  const [averageRating, setAverageRating] = useState(0);
  const [hasReviewed, setHasReviewed] = useState(false);
  const [foundBathroom, setFoundBathroom] = useState(null);

  useEffect(() => {
    const findBathroom = async () => {
      try {
        const addressResponse = await axios.get(`http://localhost:8080/api/address/lat/${bathroom.latitude}/long/${bathroom.longitude}`);
        if (addressResponse.status === 200) {
          const address = addressResponse.data;
          const bathroomResponse = await axios.get(`http://localhost:8080/api/bathroom/${bathroom.name}/address/${address.addressId}`);
          if (bathroomResponse.status === 200) {
            const foundBathroomData = bathroomResponse.data;
            console.log('Found bathroom:', foundBathroomData);
            setFoundBathroom(foundBathroomData);
            setBathroomId(foundBathroomData.bathroomId);
          } else {
            console.error('Bathroom not found for the given address.');
            setFoundBathroom(null);
            setBathroomId(null);
          }
        } else {
          console.error('Address not found for the given latitude and longitude.');
          setFoundBathroom(null);
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
          if (response.data.length > 0) {
            const total = response.data.reduce((acc, rating) => acc + rating.rating, 0);
            const average = (total / response.data.length).toFixed(1);
            setAverageRating(average);
            const userHasReviewed = response.data.some(rating => rating.userId == userId);
            setHasReviewed(userHasReviewed);
          } else {
            setAverageRating(0);
            setHasReviewed(false);
          }
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
      <div className='flex justify-between w-full max-w-lg'>
        <Link to='/bathrooms' className='text-[#747FFF] font-semibold hover:underline mb-6'>
          ← To Bathrooms
        </Link>
        <Link to='/map' className='text-[#747FFF] font-semibold hover:underline mb-6 text-right'>
          To Map →
        </Link>
      </div>

      <div className='bg-white p-6 shadow-md rounded-lg max-w-[36rem] w-full'>
        {error && <p className='text-red-500'>{error}</p>}

        {(foundBathroom || bathroom) && (
          <div>
            <div className='flex justify-between items-center mb-4'>
              <h2 className='text-2xl font-bold text-gray-800'>{foundBathroom ? foundBathroom.name : bathroom.name}</h2>
              <p className='text-gray-600'>
                {averageRating ? (
                  <ReactStars count={5} value={parseFloat(averageRating)} edit={false} size={24} activeColor='#ffd700' />
                ) : (
                  <span className='font-normal'>No ratings yet</span>
                )}
              </p>
            </div>

            <div className='mb-4'>
              <p className='text-gray-600 my-2'>
                <span className='font-semibold text-lg'>Address: </span>
                {foundBathroom ? foundBathroom.address.street : bathroom.street}
              </p>

              {bathroom.directions && (
                <p className='text-gray-600 my-2'>
                  <span className='font-semibold text-lg'>Direction: </span>
                  {bathroom.directions}
                </p>
              )}

              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <BiMaleFemale className='icon' />
                <span className='font-semibold text-lg'>Unisex: </span>
                {bathroom.unisex ? 'Yes' : 'No'}
              </p>
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <FaBaby className='icon' />
                <span className='font-semibold text-lg'>Changing Station:</span>
                {bathroom.changing_station ? 'Yes' : 'No'}
              </p>
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <BiAccessibility className='icon' />
                <span className='font-semibold text-lg'>Accessibility: </span>
                {bathroom.accessible ? 'Yes' : 'No'}
              </p>
            </div>
          </div>
        )}

        <div className='flex justify-between'>
          <a
            href={`https://www.google.com/maps/dir/?api=1&destination=${foundBathroom ? foundBathroom.address.latitude : bathroom.latitude},${
              foundBathroom ? foundBathroom.address.longitude : bathroom.longitude
            }`}
            target='_blank'
            rel='noopener noreferrer'
            className='btn btn-primary text-white'
          >
            Get Directions
          </a>
          {user ? (
            hasReviewed ? (
              <button className='btn btn-secondary text-white mb-4' disabled>
                Already Reviewed
              </button>
            ) : (
              <button className='btn btn-accent text-white mb-4' onClick={handleAddBathroom}>
                Add Review
              </button>
            )
          ) : null}
        </div>

        <div className='mt-4'>
          <h3 className='text-xl font-semibold text-gray-600'>Ratings:</h3>
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
    </div>
  );
};

export default BathroomDetail;
