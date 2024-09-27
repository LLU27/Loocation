import { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../components/AuthContext';
import BathroomModal from '../components/BathroomModal';
import ReactStars from 'react-rating-stars-component';

const UserBathroom = () => {
  const { userId } = useAuth();
  const [bathroomIds, setBathroomIds] = useState([]);
  const [bathrooms, setBathrooms] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [selectedBathroom, setSelectedBathroom] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchUserBathroomIds = async () => {
      if (!userId) {
        setLoading(false);
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/api/userbathroom/user/${userId}`);
        setBathroomIds(response.data);
      } catch (err) {
        setError('Error fetching user bathroom IDs: ' + err.message);
        console.error('Error fetching user bathroom IDs: ', err);
      } finally {
        setLoading(false);
      }
    };

    fetchUserBathroomIds();
  }, [userId]);
  useEffect(() => {
    const fetchBathrooms = async () => {
      if (bathroomIds.length === 0) return;
      try {
        const promises = bathroomIds.map(async ({ bathroomId }) => {
          try {
            const bathroomResponse = await axios.get(`http://localhost:8080/api/bathroom/${bathroomId}`);
            const bathroom = bathroomResponse.data;
            const userRatingResponse = await axios.get(`http://localhost:8080/api/rating/bathroom/${bathroomId}`);
            const ratings = userRatingResponse.data;

            const userRating = ratings.find(rating => rating.userId == userId);
            if (userRating?.userId == userId) {
              bathroom.userRating = userRating.rating;
              bathroom.userComment = userRating.comment;
              return { ...bathroom, userRating: userRating.rating, userComment: userRating.comment };
            }
            return null;
          } catch (err) {
            console.error('Error fetching bathroom details or user rating: ', err);
            return null;
          }
        });

        const results = await Promise.all(promises);
        setBathrooms(results.filter(bathroom => bathroom !== null)); // Filter out nulls
      } catch (err) {
        setError('Error fetching bathrooms: ' + err.message);
        console.error('Error fetching bathrooms: ', err);
      }
    };

    fetchBathrooms();
  }, [bathroomIds, userId]);

  const handleBathroomClick = bathroom => {
    setSelectedBathroom(bathroom);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedBathroom(null);
  };

  if (loading) {
    return <div className='text-center mt-6'>Loading...</div>;
  }

  if (error) {
    return <div className='text-red-500 text-center mt-6'>{error}</div>;
  }

  return (
    <div>
      <div className='max-w-3xl my-6 mx-auto p-6 bg-white rounded-lg shadow-lg'>
        <h2 className='text-3xl font-bold text-center mb-6 text-gray-800'>My Bathrooms</h2>

        <ul className='space-y-4'>
          {bathrooms.length > 0 ? (
            bathrooms.map(bathroom => (
              <li
                key={bathroom.bathroomId}
                className='border rounded-lg p-4 hover:shadow-lg transition-shadow duration-300 cursor-pointer'
                onClick={() => handleBathroomClick(bathroom)}
              >
                <h3 className='text-xl font-semibold text-gray-700'>{bathroom.name}</h3>
                <p className='mt-2 text-sm text-gray-500'>
                  Accessibility: {bathroom.accessibility ? 'Yes' : 'No'}, Changing Station: {bathroom.changing_station ? 'Yes' : 'No'}, Unisex:{' '}
                  {bathroom.unisex ? 'Yes' : 'No'}
                </p>

                <div className='mt-2'>
                  <p className='text-sm text-gray-500'>Your Rating:</p>
                  <ReactStars count={5} value={bathroom.userRating || 0} edit={false} size={24} activeColor='#ffd700' />
                  <p className='text-sm text-gray-500 mt-2'>{bathroom.userComment}</p>
                </div>
              </li>
            ))
          ) : (
            <li className='text-gray-500 text-center'>No bathrooms found for this user.</li>
          )}
        </ul>
      </div>

      <BathroomModal bathroom={selectedBathroom} isOpen={isModalOpen} onClose={closeModal} />
    </div>
  );
};

export default UserBathroom;
