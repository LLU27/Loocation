import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Home, About, Bathrooms } from './view';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/about' element={<About />} />
        <Route path='/bathrooms' element={<Bathrooms />} />
        <Route path='*' element={<div>Not Found</div>} />
      </Routes>
    </Router>
  );
};

export default App;
