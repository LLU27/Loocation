import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png';

const NavBar = () => {
  return (
    <nav className='bg-gray-800 text-white py-4'>
      <div className='container mx-auto flex items-center justify-between'>
        <div className='flex items-center'>
          <Link to='/'>
            <img src={logo} alt='Logo' className='h-8 w-auto' />
          </Link>
        </div>

        {/* Navigation Links */}
        <div className='space-x-4'>
          <Link to='/' className='hover:text-gray-400'>
            Home
          </Link>
          <Link to='/about' className='hover:text-gray-400'>
            About
          </Link>
        </div>

        {/* Authentication Links */}
        <div className='space-x-4'>
          <Link to='/login' className='bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded'>
            Login
          </Link>
          <Link to='/signup' className='bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded'>
            Signup
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
