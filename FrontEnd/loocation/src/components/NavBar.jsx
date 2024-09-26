import { Link } from 'react-router-dom';
import logo from '../assets/logo.png';
import { useAuth } from './AuthContext';

const NavBar = () => {
  const { user, userId, logout } = useAuth();
  console.log(user);
  return (
    <nav className='bg-gray-800 text-white py-4'>
      <div className='container mx-auto flex items-center justify-between'>
        <div className='flex items-center w-1/3'>
          <Link to='/'>
            <img src={logo} alt='Logo' className='h-12 w-auto' />
          </Link>
        </div>

        <div className='space-x-6 flex justify-evenly w-1/3'>
          <Link to='/' className='hover:text-gray-400'>
            Home
          </Link>
          <Link to='/bathrooms' className='hover:text-gray-400'>
            Bathrooms
          </Link>
          <Link to='/map' className='hover:text-gray-400'>
            Map
          </Link>
          {user && (
            <Link to={`/user/${userId}/bathrooms`} className='hover:text-gray-400'>
              Saved Bathrooms
            </Link>
          )}
        </div>

        <div className='space-x-4 w-1/3 flex justify-end items-center'>
          {user ? (
            <>
              <span className='text-white'>Hi, {user}</span>
              <button onClick={logout} className='btn btn-primary text-white'>
                Logout
              </button>
            </>
          ) : (
            <Link to='/login' className='btn btn-primary text-white'>
              Login
            </Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
