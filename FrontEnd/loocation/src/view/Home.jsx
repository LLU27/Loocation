const Home = () => {
  return (
    <div className='flex items-center justify-center min-h-screen '>
      <div className=' p-8 rounded-lg shadow-lg max-w-md w-full'>
        <h1 className='text-3xl font-bold mb-4 text-center '>Looking For A Bathroom?</h1>
        <form>
          <div className='flex flex-col gap-4'>
            <input className='input input-bordered w-full' type='text' placeholder='Enter Your Zipcode' />
            <button type='submit' className='btn btn-primary w-full'>
              Loo Around
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Home;
