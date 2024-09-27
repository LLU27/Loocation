import GoogleMap from '../components/GoogleMap';
import { useState, useEffect } from 'react';
import axios from 'axios';

const MapView = ({ bathrooms, coords, handleLooAround, loading, setLoading }) => {
  const [dbBathrooms, setDbBathrooms] = useState([]);
  const [error, setError] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const fetchDBBathrooms = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/bathroom`);
        setDbBathrooms(response.data);
      } catch (err) {
        setError('Error fetching bathroom details');
        console.error('Error fetching bathroom details: ', err);
      }
    };
    fetchDBBathrooms();
  }, []);

  const handleLooAroundClick = () => {
    setLoading(true);
    setErrorMessage('');
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const { latitude, longitude } = position.coords;
          handleLooAround(latitude, longitude);
        },
        error => {
          setLoading(false);
          console.error('Error getting location:', error);
          setErrorMessage('Unable to retrieve your location. Please enable location services and try again.');
        }
      );
    } else {
      setLoading(false);
      setErrorMessage('Geolocation is not supported by this browser.');
    }
  };

  return (
    <div>
      {coords.lat && coords.lng ? (
        <GoogleMap
          lat={coords.lat}
          long={coords.lng}
          bathrooms={bathrooms}
          dbBathrooms={dbBathrooms}
          handleLooAround={handleLooAround}
          loading={loading}
          setLoading={setLoading}
        />
      ) : (
        <>
          <p>Coordinates not available</p>
          <button className='btn btn-primary' onClick={handleLooAroundClick}>
            Refresh
          </button>
        </>
      )}
      {error && <p className='text-red-500'>{error}</p>}
    </div>
  );
};

export default MapView;
