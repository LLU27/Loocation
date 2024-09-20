import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import bg from '../assets/bg.jpeg';

const Home = ({ onLooAround, loading, setLoading }) => {
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLooAroundClick = () => {
    setError(null);
    setLoading(true);

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const { latitude, longitude } = position.coords;
          onLooAround(latitude, longitude);
          setTimeout(() => {
            navigate('/bathrooms');
          }, 2000);
        },
        err => {
          setError('Unable to retrieve location. Please try again.');
          console.error('Error getting location:', err);
          setLoading(false);
        },
        {
          enableHighAccuracy: true,
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
    <div className='flex items-center justify-center min-h-screen bg-cover bg-center' style={{ backgroundImage: `url(${bg})` }}>
      <div className='p-12 rounded-lg shadow-lg max-w-md w-full bg-gray-800 bg-opacity-95'>
        <h1 className='text-3xl font-bold mb-4 text-center text-white'>Find Your Dream Destination</h1>
        <div className='flex flex-col gap-4'>
          <button onClick={handleLooAroundClick} className='btn btn-primary w-full text-white' disabled={loading}>
            {loading ? 'Finding location...' : 'Loo Around'}
          </button>
          {loading && <div className='spinner'></div>}

          {error && <p className='mt-4 text-red-500'>{error}</p>}
        </div>
      </div>
    </div>
  );
};

export default Home;
