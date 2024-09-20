import { useParams, useLocation, Link } from 'react-router-dom';
import { BiMaleFemale } from 'react-icons/bi';
import { FaBaby } from 'react-icons/fa6';
import { BiAccessibility } from 'react-icons/bi';
import { useAuth } from '../components/AuthContext';

const BathroomDetail = () => {
  const { id } = useParams();
  const location = useLocation();
  const { user } = useAuth(); // Get the user from context

  const bathroom = location.state;
  const handleAddToList = () => {
    // Logic to add the bathroom to the user's list
    console.log(`Added bathroom with ID: ${id}`);
  };

  return (
    <div className='flex flex-col items-center mt-8'>
      <Link to='/bathrooms' className='text-blue-500 hover:underline mb-6'>
        ← Back to Bathrooms
      </Link>

      <div className='bg-white p-6 shadow-md rounded-lg max-w-md w-full'>
        {bathroom ? (
          <div>
            <h2 className='text-2xl font-bold text-gray-800 mb-4'>{bathroom.name}</h2>
            <div className='mb-4'>
              <p className=' text-gray-600 my-2'>
                <span className='font-semibold text-lg'>Address: </span>
                {bathroom.street}
              </p>
              {bathroom.directions && (
                <p className='text-gray-600 my-2'>
                  <span className='font-semibold text-lg'>Direction: </span>
                  {bathroom.directions}
                </p>
              )}
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <BiMaleFemale />
                <span className='font-semibold text-lg'>Unisex: </span>
                {bathroom.unisex ? 'Yes' : 'No'}
              </p>
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <FaBaby />
                <span className='font-semibold text-lg'>Changing Station:</span>
                {bathroom.changing_table ? 'Yes' : 'No'}
              </p>
              <p className='text-gray-600 my-2 flex items-center gap-1'>
                <BiAccessibility />
                <span className='font-semibold text-lg'>Accessibility: </span>
                {bathroom.accessible ? 'Yes' : 'No'}
              </p>
            </div>

            <div className='flex justify-between'>
              <a
                href={`https://www.google.com/maps/dir/?api=1&destination=${bathroom.latitude},${bathroom.longitude}`}
                target='_blank'
                rel='noopener noreferrer'
                className='btn btn-primary text-white '
              >
                Get Directions
              </a>
              {user && (
                <button onClick={handleAddToList} className='btn btn-accent text-white mb-4'>
                  Add to My Bathroom List
                </button>
              )}
            </div>
          </div>
        ) : (
          <p className='text-lg text-gray-500'>Bathroom data not available.</p>
        )}
      </div>
    </div>
  );
};

export default BathroomDetail;
