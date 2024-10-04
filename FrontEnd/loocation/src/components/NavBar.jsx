import { Link, useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import { useAuth } from './AuthContext';
import { useState } from 'react';
import { RxHamburgerMenu } from 'react-icons/rx';
const NavBar = () => {
  const { user, userId, logout } = useAuth();
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  const handleLogout = () => {
    navigate('/');
    logout();
  };

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <nav className='bg-gray-800 text-white'>
      <div className='container mx-auto flex items-center justify-between py-4'>
        <div className='flex items-center'>
          <Link to='/'>
            <img src={logo} alt='Logo' className='h-12 w-auto' />
          </Link>
        </div>

        <div className='lg:hidden'>
          <button onClick={toggleMenu} className='text-white focus:outline-none'>
            <RxHamburgerMenu className='w-6 h-6' />
          </button>
        </div>

        <div className='hidden lg:flex space-x-6'>
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

        <div className='hidden lg:flex space-x-4 items-center'>
          {user ? (
            <>
              <span className='text-white'>Hi, {user}</span>
              <button onClick={handleLogout} className='btn btn-primary text-white'>
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

      {isOpen && (
        <div className='lg:hidden'>
          <div className='px-2 pt-2 pb-3 space-y-1'>
            <Link to='/' className='block text-white py-2 text-center hover:bg-gray-700'>
              Home
            </Link>
            <Link to='/bathrooms' className='block text-white py-2 text-center hover:bg-gray-700'>
              Bathrooms
            </Link>
            <Link to='/map' className='block text-white py-2 text-center hover:bg-gray-700'>
              Map
            </Link>
            {user && (
              <Link to={`/user/${userId}/bathrooms`} className='block text-white py-2 text-center hover:bg-gray-700'>
                Saved Bathrooms
              </Link>
            )}
            {user ? (
              <button onClick={handleLogout} className='block w-1/2 mx-auto py-2 text-center btn btn-primary'>
                Logout
              </button>
            ) : (
              <Link to='/login' className='block w-full py-2 text-center btn btn-primary'>
                Login
              </Link>
            )}
          </div>
        </div>
      )}
    </nav>
  );
};

export default NavBar;
