import React from 'react';

const BathroomCard = ({ name, address, latitude, longitude, street, city, state }) => {
  const clickHandler = () => {
    window.open(`https://www.google.com/maps/dir/?api=1&destination=${latitude},${longitude}`);
  };
  return (
    <div className='bg-gray-700 text-white p-4 rounded-lg shadow-md'>
      <h2 className='text-xl font-bold'>{name}</h2>
      <p className='mt-2'>{address}</p>
      <p className=''>{street}</p>
      <p className=''>{city}</p>
      <p className=''>{state}</p>

      <button className='btn btn-primary mt-4' onClick={clickHandler}>
        Get Directions
      </button>
    </div>
  );
};

export default BathroomCard;
