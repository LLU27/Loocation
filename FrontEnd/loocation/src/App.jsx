import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Home, About, Bathrooms, Login, Register } from './view';
import NavBar from './components/NavBar';

const App = () => {
  return (
    <Router>
      <NavBar />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/about' element={<About />} />
        <Route path='/bathrooms' element={<Bathrooms />} />
        <Route path='/login' element={<Login />} />
        <Route path='/signup' element={<Register />} />
        <Route path='*' element={<div>Not Found</div>} />
      </Routes>
    </Router>
  );
};

export default App;
