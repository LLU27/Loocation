import BathroomCard from '../components/BathroomCard';

const Bathrooms = ({ bathrooms }) => {
  console.log(bathrooms);

  return (
    <div className='flex flex-col items-center'>
      <h1 className='text-2xl font-bold text-center mt-4'>{`Your Location: ${bathrooms[0]?.city}, ${bathrooms[0]?.state}`}</h1>

      <div className='grid grid-cols-1 gap-4 p-4 mt-6 max-w-4xl'>
        {bathrooms.map((bathroom, index) => (
          <BathroomCard
            key={index}
            name={bathroom.name}
            address={bathroom.address}
            latitude={bathroom.latitude}
            longitude={bathroom.longitude}
            street={bathroom.street}
            state={bathroom.state}
            city={bathroom.city}
          />
        ))}
      </div>
    </div>
  );
};

export default Bathrooms;
