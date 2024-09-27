import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import bg from '../assets/bg.jpeg';

const Home = ({ onLooAround, loading, setLoading, bathrooms }) => {
  const [error, setError] = useState(null);
  const [coords, setCoords] = useState(() => ({ lat: 0, lng: 0 }));
  const navigate = useNavigate();

  const handleLooAroundClick = () => {
    setError(null);
    setLoading(true);

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const { latitude, longitude } = position.coords;
          setCoords({ lat: latitude, lng: longitude });
          onLooAround(latitude, longitude);
          setTimeout(() => {
            navigate('/map');
          }, 2000);
        },
        err => {
          setError('Unable to retrieve location. Please try again.');
          console.error('Error getting location:', err);
          setLoading(false);
        },
        {
          enableHighAccuracy: false,
          timeout: 5000,
          maximumAge: 0,
        }
      );
    } else {
      setError('Geolocation is not supported by this browser.');
      setLoading(false);
    }
  };

  return (
    <div className='flex flex-col items-center  min-h-screen bg-cover bg-center relative gap-8 ' style={{ backgroundImage: `url(${bg})` }}>
      <div className='absolute inset-0 bg-black opacity-60 ' />

      <h1 className=' text-7xl z-10 font-extrabold mt-[25vh]'>LOOCATION</h1>
      <div className='relative z-10 p-8 rounded-lg shadow-lg max-w-md w-full bg-gray-800 bg-opacity-90  '>
        <h2 className='text-3xl font-bold mb-6 text-center text-white'> It's Potty Time! 🎉 🎉</h2>
        <div className='flex flex-col gap-4'>
          <button
            onClick={handleLooAroundClick}
            className='btn btn-primary text-white font-semibold py-2 px-4 rounded-lg transition duration-300'
            disabled={loading}
          >
            {loading ? 'Finding location...' : 'Loo Around'}
          </button>
          {loading && (
            <div className='flex justify-center items-center'>
              <div className='spinner'></div>
            </div>
          )}
          {error && <p className='mt-4 text-red-400 font-semibold'>{error}</p>}
        </div>
      </div>
    </div>
  );
};

export default Home;
