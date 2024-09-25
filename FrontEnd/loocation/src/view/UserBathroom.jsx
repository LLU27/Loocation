import { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../components/AuthContext';

const UserBathroom = () => {
  const { userId } = useAuth();
  const [bathroomIds, setBathroomIds] = useState([]);
  const [bathrooms, setBathrooms] = useState([]);
  const [error, setError] = useState('');


  useEffect(() => {
    const fetchUserBathroomIds = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/userbathroom/user/${userId}`);
        setBathroomIds(response.data);
      } catch (err) {
        setError(err.message);
        console.error('Error fetching user bathroom IDs: ', err);
      }
    };

    fetchUserBathroomIds();
  }, [userId]);

  useEffect(() => {
    const fetchBathrooms = async () => {
      const promises = bathroomIds.map(async ({ bathroomId }) => {
        try {
          const response = await axios.get(`http://localhost:8080/api/bathroom/${bathroomId}`);
          return response.data;
        } catch (err) {
          console.error('Error fetching bathroom details: ', err);
          return null;
        }
      });

      const results = await Promise.all(promises);
      setBathrooms(results);
    };

    if (bathroomIds.length > 0) {
      fetchBathrooms();
    }
  }, [bathroomIds]);
  

  return (
    <div>
      <div className='max-w-3xl mx-auto p-6 bg-white rounded-lg shadow-lg '>
        <h2 className='text-3xl font-bold text-center mb-6 text-gray-800'>My Bathrooms</h2>
        {error && <div className='bg-red-500 text-white p-2 rounded mb-4'>{error}</div>}
        <ul className='space-y-4'>
          {bathrooms.length > 0 ? (
            bathrooms.map(bathroom => (
              <li key={bathroom.bathroom_id} className='border rounded-lg p-4 hover:shadow-lg transition-shadow duration-300'>
                <h3 className='text-xl font-semibold text-gray-700'>{bathroom.name}</h3>

                <p className='mt-2 text-sm text-gray-500'>
                  Accessibility: {bathroom.accessibility ? 'Yes' : 'No'}, Changing Station: {bathroom.changing_station ? 'Yes' : 'No'}, Unisex:{' '}
                  {bathroom.unisex ? 'Yes' : 'No'}
                </p>
              </li>
            ))
          ) : (
            <li className='text-gray-500 text-center'>No bathrooms found for this user.</li>
          )}
        </ul>
      </div>
    </div>
  );
};
export default UserBathroom;
