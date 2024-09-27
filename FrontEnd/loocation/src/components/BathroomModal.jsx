import { BiMaleFemale } from 'react-icons/bi';
import { FaBaby } from 'react-icons/fa6';
import { BiAccessibility } from 'react-icons/bi';
import { AiOutlineClose } from 'react-icons/ai'; // Import the close icon

const BathroomModal = ({ bathroom, isOpen, onClose }) => {
  if (!isOpen) return null;
  const handleGetDirections = () => {
    const { latitude, longitude } = bathroom;
    const url = `https://www.google.com/maps/dir/?api=1&destination=${latitude},${longitude}`;
    window.open(url, '_blank');
  };
  return (
    <div className='fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50'>
      <div className='bg-white p-6 rounded-lg shadow-lg max-w-md w-full text-gray-700 relative'>
        <button className='absolute top-2 right-2 text-gray-500 hover:text-gray-700' onClick={onClose}>
          <AiOutlineClose size={24} />
        </button>
        <h2 className='text-2xl font-bold'>{bathroom.name}</h2>
        <p>
          <strong>Address:</strong> {bathroom.address?.street || 'N/A'}
        </p>
        <p className='flex items-center gap-1'>
          <BiMaleFemale /> <strong>Unisex:</strong> {bathroom.unisex ? 'Yes' : 'No'}
        </p>
        <p className='flex items-center gap-1'>
          <FaBaby /> <strong>Changing Station:</strong> {bathroom.changing_station ? 'Yes' : 'No'}
        </p>
        <p className='flex items-center gap-1'>
          <BiAccessibility /> <strong>Accessibility:</strong> {bathroom.accessibility ? 'Yes' : 'No'}
        </p>
        <p className=''>
          <strong>Comment:</strong>
          {bathroom.ratings.comment || 'N/A'}
        </p>
        <button className='mt-4 btn btn-primary text-white' onClick={handleGetDirections}>
          Get Directions
        </button>
      </div>
    </div>
  );
};

export default BathroomModal;
