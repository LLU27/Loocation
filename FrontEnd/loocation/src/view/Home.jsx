import bg from '../assets/bg.jpeg';
const Home = () => {
  return (
    <div className='flex items-center justify-center min-h-screen bg-cover bg-center' style={{ backgroundImage: `url(${bg})` }}>
      <div className='p-12 rounded-lg shadow-lg max-w-md w-full bg-gray-800 bg-opacity-95'>
        <h1 className='text-3xl font-bold mb-4 text-center'>Find Your Dream Destination</h1>
        <form>
          <div className='flex flex-col gap-4'>
            <input className='input input-bordered w-full' type='text' placeholder='Enter Your Zipcode' />
            <button type='submit' className='btn btn-primary w-full text-white'>
              Loo Around
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Home;
