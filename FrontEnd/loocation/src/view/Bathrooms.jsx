import { useState } from 'react';
import BathroomCard from '../components/BathroomCard';

const Bathrooms = ({ bathrooms, onLooAround, loading, setLoading }) => {
  const [filters, setFilters] = useState({
    accessible: false,
    changingStation: false,
    unisex: false,
  });

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

  const filteredBathrooms = bathrooms.filter(bathroom => {
    const { accessible, changing_table, unisex } = bathroom;
    return (!filters.accessible || accessible) && (!filters.changingStation || changing_table) && (!filters.unisex || unisex);
  });

  const handleFilterChange = event => {
    const { name, checked } = event.target;
    setFilters(prev => ({ ...prev, [name]: checked }));
  };

  return (
    <div className='flex flex-col items-center'>
      {loading ? (
        <div className='flex justify-center items-center h-screen'>
          <div className='spinner'></div>
        </div>
      ) : filteredBathrooms.length > 0 ? (
        <>
          <h1 className='text-2xl font-bold text-center mt-4'>{`Your Location: ${filteredBathrooms[0]?.city}, ${filteredBathrooms[0]?.state}`}</h1>

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

          <div className='grid grid-cols-1 gap-4 p-4 mt-6 max-w-4xl'>
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
      ) : (
        <div className='text-center mt-10'>
          <p className='mb-4 text-lg'>Nothing here yet.</p>
          <button onClick={handleLooAroundClick} className='btn btn-primary text-white'>
            Let's Loo Around
          </button>
        </div>
      )}
    </div>
  );
};

export default Bathrooms;
