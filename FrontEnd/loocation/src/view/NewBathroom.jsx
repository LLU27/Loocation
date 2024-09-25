import React, { useState } from 'react';
import axios from 'axios';

const NewBathroom = () => {
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async e => {
    e.preventDefault();
    setError('');
    setSuccess('');

    const newBathroomData = {
      name,
      address,
      city,
      state,
    };

    try {
      const response = await axios.post('http://localhost:8080/api/bathroom', newBathroomData);
      console.log(response);
      setSuccess('Bathroom added successfully!');
      setName('');
      setAddress('');
      setCity('');
      setState('');
    } catch (err) {
      setError('Failed to add bathroom. Please try again.');
      console.error(err);
    }
  };

  return (
    <div className='flex items-center justify-center min-h-screen bg-gray-100'>
      <div className='max-w-md w-full p-6 bg-white rounded-lg shadow-lg'>
        <h2 className='text-3xl font-bold mb-4 text-center text-gray-700'>Add New Bathroom</h2>
        {error && <div className='bg-red-500 text-white p-2 rounded mb-4'>{error}</div>}
        {success && <div className='bg-green-500 text-white p-2 rounded mb-4'>{success}</div>}
        <form onSubmit={handleSubmit} className='space-y-4'>
          <div>
            <label htmlFor='name' className='block text-gray-700'>
              Bathroom Name
            </label>
            <input
              type='text'
              id='name'
              value={name}
              onChange={e => setName(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded'
            />
          </div>
          <div>
            <label htmlFor='address' className='block text-gray-700'>
              Address
            </label>
            <input
              type='text'
              id='address'
              value={address}
              onChange={e => setAddress(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded'
            />
          </div>
          <div>
            <label htmlFor='city' className='block text-gray-700'>
              City
            </label>
            <input
              type='text'
              id='city'
              value={city}
              onChange={e => setCity(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded'
            />
          </div>
          <div>
            <label htmlFor='state' className='block text-gray-700'>
              State
            </label>
            <input
              type='text'
              id='state'
              value={state}
              onChange={e => setState(e.target.value)}
              required
              className='w-full p-2 border border-gray-300 rounded'
            />
          </div>
          <button type='submit' className='btn btn-primary w-full p-2 rounded transition duration-200 text-white'>
            Add Bathroom
          </button>
        </form>
      </div>
    </div>
  );
};

export default NewBathroom;
