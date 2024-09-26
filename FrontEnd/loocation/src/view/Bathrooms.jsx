import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import BathroomCard from '../components/BathroomCard';
import axios from 'axios';
import { useAuth } from '../components/AuthContext';

const Bathrooms = ({ bathrooms, onLooAround, loading, setLoading }) => {
  const { userId } = useAuth();

  const [bathroomIds, setBathroomIds] = useState([]);
  const [userBathrooms, setUserBathrooms] = useState([]);
  const [error, setError] = useState('');
  const [filters, setFilters] = useState({
    accessible: false,
    changingStation: false,
    unisex: false,
  });

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
      try {
        const promises = bathroomIds.map(async ({ bathroomId }) => {
          const response = await axios.get(`http://localhost:8080/api/bathroom/${bathroomId}`);
          return response.data;
        });
        const results = await Promise.all(promises);
        setUserBathrooms(results.filter(bathroom => bathroom !== null));
      } catch (err) {
        setError('Error fetching bathroom details');
        console.error('Error fetching bathroom details: ', err);
      }
    };
    if (bathroomIds.length > 0) {
      fetchBathrooms();
    }
  }, [bathroomIds]);

  const handleLooAroundClick = () => {
    setLoading(true);
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const { latitude, longitude } = position.coords;
          onLooAround(latitude, longitude);
        },
        error => {
          console.error('Error getting location:', error);
        }
      );
    } else {
      console.error('Geolocation is not supported by this browser.');
    }
  };

  const allBathrooms = [...userBathrooms, ...bathrooms];
  console.log('allBathrooms', allBathrooms);
  const filteredBathrooms = allBathrooms.filter(bathroom => {
    const { accessible, changing_table, unisex } = bathroom;
    const matchesAccessible = !filters.accessible || accessible;
    const matchesChangingStation = !filters.changingStation || changing_table;
    const matchesUnisex = !filters.unisex || unisex;
    if (filters.accessible || filters.changingStation || filters.unisex) {
      return matchesAccessible && matchesChangingStation && matchesUnisex;
    }
    return true;
  });

  const handleFilterChange = event => {
    const { name, checked } = event.target;
    setFilters(prev => ({ ...prev, [name]: checked }));
  };

  const hasFilters = filters.accessible || filters.changingStation || filters.unisex;
  const hasFilteredBathrooms = filteredBathrooms.length > 0;

  return (
    <div className='flex flex-col items-center'>
      {loading ? (
        <div className='flex justify-center items-center h-screen'>
          <div className='spinner'></div>
        </div>
      ) : (
        <>
          <h1 className='text-2xl font-bold text-center mt-4'>
            {hasFilteredBathrooms ? `Your Location: ${filteredBathrooms[0]?.city}, ${filteredBathrooms[0]?.state}` : 'Sorry! None Around You Yet :('}
          </h1>

          <div className='mb-4 flex gap-2'>
            <label className='flex items-center'>
              <input type='checkbox' name='accessible' checked={filters.accessible} onChange={handleFilterChange} />
              <span className='ml-2'>Accessible</span>
            </label>
            <label className='flex items-center ml-4'>
              <input type='checkbox' name='changingStation' checked={filters.changingStation} onChange={handleFilterChange} />
              <span className='ml-2'>Changing Station</span>
            </label>
            <label className='flex items-center ml-4'>
              <input type='checkbox' name='unisex' checked={filters.unisex} onChange={handleFilterChange} />
              <span className='ml-2'>Unisex</span>
            </label>
          </div>

          {hasFilteredBathrooms ? (
            <>
              <div className='flex gap-2 items-center'>
                <h2>Don't see a bathroom?</h2>
                <Link to='/bathroom/new' className='btn '>
                  Add Bathroom
                </Link>
              </div>

              <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 p-4 mt-6'>
                {filteredBathrooms.map((bathroom, index) => (
                  <BathroomCard
                    key={index}
                    id={bathroom.id}
                    name={bathroom.name}
                    address={bathroom.address}
                    latitude={bathroom.latitude}
                    longitude={bathroom.longitude}
                    street={bathroom.street}
                    state={bathroom.state}
                    city={bathroom.city}
                    directions={bathroom.directions}
                    changing_table={bathroom.changing_table}
                    unisex={bathroom.unisex}
                    accessible={bathroom.accessible}
                  />
                ))}
              </div>
            </>
          ) : hasFilters ? (
            <div className='text-center mt-10'>
              <p className='mb-4 text-lg'>Sorry, no bathroom found.</p>
            </div>
          ) : (
            <div className='text-center mt-10'>
              <p className='mb-4 text-lg'>Nothing here yet.</p>
              <button onClick={handleLooAroundClick} className='btn btn-primary text-white'>
                Let's Loo Around
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Bathrooms;
