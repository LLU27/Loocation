import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Signup = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate(); 

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const user = {
      username,
      email,
      password
    };

    try {
      const response = await fetch('http://localhost:8080/api/user', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
      });

      if (response.ok) {
        navigate('/home');
      } else {
        const errorData = await response.json();
        setError(errorData.message || 'An error occurred while signing up.');
      }
    } catch (err) {
      console.error('Error signing up:', err);
      setError('Error signing up. Please try again.');
    }
  };

  return (
    <div className='flex items-center justify-center min-h-screen bg-gray-200'>
      <div className='p-6 bg-gray-800 rounded-lg shadow-md max-w-sm w-full'>
        <h2 className='text-3xl font-bold mb-4 text-center'>Sign Up</h2>
        {error && <p className="text-red-500">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className='mb-4'>
            <label className='block mb-1'>Username:</label>
            <input
              type='text'
              value={username}
              onChange={e => setUsername(e.target.value)}
              className='input input-bordered w-full'
              required
            />
          </div>
          <div className='mb-4'>
            <label className='block mb-1'>Email:</label>
            <input
              type='email'
              value={email}
              onChange={e => setEmail(e.target.value)}
              className='input input-bordered w-full'
              required
            />
          </div>
          <div className='mb-4'>
            <label className='block mb-1'>Password:</label>
            <input
              type='password'
              value={password}
              onChange={e => setPassword(e.target.value)}
              className='input input-bordered w-full'
              required
            />
          </div>
          <button type='submit' className='btn btn-primary w-full text-white mt-4'>
            Sign Up
          </button>
        </form>
        <p className='mt-4 text-center'>
          Already have an account?{' '}
          <Link to='/login' className='text-blue-500 hover:underline'>
            Login
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Signup;
