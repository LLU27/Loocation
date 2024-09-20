import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import { useState } from 'react';
import Home from './view/Home';
import About from './view/About';
import Bathrooms from './view/Bathrooms';
import Login from './view/Login';
import Signup from './view/Signup';

const App = () => {
  const [bathrooms, setBathrooms] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleLooAround = async (latitude, longitude) => {
    setLoading(true);
    setError(null);

    const url = `https://public-bathrooms.p.rapidapi.com/location?lat=${latitude}&lng=${longitude}&page=1&per_page=10&offset=0`;

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
        <Route path='/' element={<Home onLooAround={handleLooAround} loading={loading} setLoading={setLoading} />} />
        <Route path='/about' element={<About />} />
        <Route path='/bathrooms' element={<Bathrooms bathrooms={bathrooms} />} />
        <Route path='/login' element={<Login />} />
        <Route path='/signup' element={<Signup />} />
        <Route path='*' element={<div>Not Found</div>} />
      </Routes>
      {error && <p className='text-red-500'>{error}</p>}
    </Router>
  );
};

export default App;
