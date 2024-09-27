import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import { useState, useEffect } from 'react';
import Home from './view/Home';
import About from './view/About';
import Bathrooms from './view/Bathrooms';
import Login from './view/Login';
import SignUp from './view/SignUp';
import BathroomDetail from './view/BathroomDetail';
import AddBathroom from './view/AddBathroom';
import UserBathroom from './view/UserBathroom';
import MapView from './view/MapView';
import NewBathroom from './view/NewBathroom';

const App = () => {
  const [bathrooms, setBathrooms] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [coords, setCoords] = useState({ lat: 0, lng: 0 });

  useEffect(() => {
    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          position => {
            const { latitude, longitude } = position.coords;
            setCoords({ lat: latitude, lng: longitude });
          },
          error => {
            console.error('Error getting location:', error);
            setError('Unable to retrieve your location.');
          }
        );
      } else {
        setError('Geolocation is not supported by this browser.');
      }
    };

    getCurrentLocation();
  }, []);

  const handleLooAround = async (latitude, longitude) => {
    setLoading(true);
    setError(null);
    setCoords({ lat: latitude, lng: longitude });
    const url = `https://public-bathrooms.p.rapidapi.com/location?lat=${latitude}&lng=${longitude}&page=1&per_page=50&offset=0`;

    const options = {
      method: 'GET',
      headers: {
        'X-Rapidapi-Key': import.meta.env.VITE_RAPID_API_KEY,
      },
    };

    try {
      const response = await fetch(url, options);
      const data = await response.json();
      console.log('Bathrooms:', data);
      setBathrooms(data);
    } catch (err) {
      console.error('Error fetching bathrooms:', err);
      setError('Error fetching bathrooms data. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Router>
      <NavBar />
      <Routes>
        <Route path='/' element={<Home onLooAround={handleLooAround} loading={loading} setLoading={setLoading} bathrooms={bathrooms} />} />
        <Route path='/about' element={<About />} />
        <Route
          path='/bathrooms'
          element={<Bathrooms bathrooms={bathrooms} onLooAround={handleLooAround} loading={loading} setLoading={setLoading} />}
        />
        <Route path='/bathroom/:id' element={<BathroomDetail />} />
        <Route path='/login' element={<Login />} />
        <Route path='/signup' element={<SignUp />} />
        <Route path='/add/bathroom/:id' element={<AddBathroom />} />
        <Route path='/user/:id/bathrooms' element={<UserBathroom />} />
        <Route path='/bathroom/new' element={<NewBathroom />} />
        <Route
          path='/map'
          element={<MapView bathrooms={bathrooms} coords={coords} handleLooAround={handleLooAround} loading={loading} setLoading={setLoading} />}
        />
        <Route path='*' element={<div>Not Found</div>} />
      </Routes>
      {error && <p className='text-red-500'>{error}</p>}
    </Router>
  );
};

export default App;
