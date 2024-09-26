import React from 'react';
import { useNavigate } from 'react-router-dom';
import { BiMaleFemale } from 'react-icons/bi';
import { FaBaby } from 'react-icons/fa6';
import { BiAccessibility } from 'react-icons/bi';

const BathroomCard = ({ id, name, address, latitude, longitude, street, city, state, directions, unisex, changing_table, accessible }) => {
  const navigate = useNavigate();

  const handleNavigate = () => {
    navigate(`/bathroom/${id}`, {
      state: { id, name, latitude, longitude, street, city, state, directions, unisex, changing_table, accessible },
    });
  };

  const clickHandler = e => {
    e.stopPropagation();
    window.open(`https://www.google.com/maps/dir/?api=1&destination=${latitude},${longitude}`);
  };

  return (
    <div className='bg-gray-700 text-white p-4 rounded-lg shadow-md min-w-[400px]'>
      <h2 className='text-xl font-bold'>{name}</h2>
      {address ? (
        <>
          <p>{address.street}</p>
          <p>{address.city}</p>
          <p className='mb-2'>{address.state}</p>
        </>
      ) : (
        <>
          <p>{street}</p>
          <p>{city}</p>
          <p className='mb-2'>{state}</p>
        </>
      )}
      <div className='flex gap-2'>
        {unisex && <BiMaleFemale />}
        {changing_table && <FaBaby />}
        {accessible && <BiAccessibility />}
      </div>
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
