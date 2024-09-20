import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../components/AuthContext';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        throw new Error('Invalid credentials');
      }

      const data = await response.json();
      login(data.username);
      navigate('/');
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div className='flex items-center justify-center min-h-screen bg-gray-200'>
      <div className='p-6 bg-gray-800 rounded-lg shadow-md max-w-sm w-full'>
        <h2 className='text-3xl font-bold mb-4 text-center'>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className='mb-4'>
            <label className='block mb-1'>Email:</label>
            <input type='email' value={email} onChange={e => setEmail(e.target.value)} className='input input-bordered w-full' required />
          </div>
          <div className='mb-4'>
            <label className='block mb-1'>Password:</label>
            <input type='password' value={password} onChange={e => setPassword(e.target.value)} className='input input-bordered w-full' required />
          </div>
          {error && <p className='text-red-500'>{error}</p>}
          <button type='submit' className='btn btn-primary w-full text-white mt-4'>
            Login
          </button>
        </form>
        <p className='mt-4 text-center'>
          Don’t have an account?{' '}
          <Link to='/signup' className='text-blue-500 hover:underline'>
            Sign Up
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
