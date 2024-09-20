import BathroomCard from '../components/BathroomCard';

const Bathrooms = ({ bathrooms, onLooAround, loading, setLoading }) => {
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

  return (
    <div className='flex flex-col items-center'>
      {loading ? (
        <div className='flex justify-center items-center h-screen'>
          <div className='spinner'></div>
        </div>
      ) : bathrooms.length > 0 ? (
        <>
          <h1 className='text-2xl font-bold text-center mt-4'>{`Your Location: ${bathrooms[0]?.city}, ${bathrooms[0]?.state}`}</h1>
          <div className='grid grid-cols-1 gap-4 p-4 mt-6 max-w-4xl'>
            {bathrooms.map((bathroom, index) => (
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
