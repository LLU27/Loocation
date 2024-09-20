import React from 'react';
import { useNavigate } from 'react-router-dom';

const BathroomCard = ({ id, name, address, latitude, longitude, street, city, state, directions, unisex, changing_table, accessible }) => {
  const navigate = useNavigate();

  const handleNavigate = () => {
    navigate(`/bathroom/${id}`, {
      state: { id, name, address, latitude, longitude, street, city, state, directions, unisex, changing_table, accessible },
    });
  };

  const clickHandler = e => {
    e.stopPropagation();
    window.open(`https://www.google.com/maps/dir/?api=1&destination=${latitude},${longitude}`);
  };

  return (
    <div className='bg-gray-700 text-white p-4 rounded-lg shadow-md'>
      <h2 className='text-xl font-bold'>{name}</h2>
      <p className='mt-2'>{address}</p>
      <p className=''>{street}</p>
      <p className=''>{city}</p>
      <p className=''>{state}</p>

      <div className='flex gap-4 my-4'>
        <button className='btn btn-primary text-white' onClick={clickHandler}>
          Get Directions
        </button>
        <button className='btn text-white' onClick={handleNavigate}>
          More Details
        </button>
      </div>
    </div>
  );
};

export default BathroomCard;
