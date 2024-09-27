import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { APIProvider, Map, AdvancedMarker, Pin, InfoWindow } from '@vis.gl/react-google-maps';
import { mapConfiguration } from '../../mapConfiguration';

const GoogleMap = ({ lat, long, bathrooms, dbBathrooms, handleLooAround, setLoading, loading }) => {
  const [openPublicInfoWindow, setOpenPublicInfoWindow] = useState(false);
  const [openDbInfoWindow, setOpenDbInfoWindow] = useState(false);
  const [selectedPublicBathroom, setSelectedPublicBathroom] = useState(null);
  const [selectedDbBathroom, setSelectedDbBathroom] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');
  const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;
  const position = { lat, lng: long };
  const navigate = useNavigate();

  const handleNavigate = () => {
    if (selectedPublicBathroom || selectedDbBathroom) {
      const bathroom = selectedPublicBathroom || selectedDbBathroom;
      const {
        id,
        name,
        street = selectedPublicBathroom ? bathroom.street : bathroom.address.street,
        city = selectedPublicBathroom ? bathroom.city : bathroom.address.city,
        state = selectedPublicBathroom ? bathroom.state : bathroom.address.state,
        latitude = selectedPublicBathroom ? bathroom.latitude : bathroom.address.latitude,
        longitude = selectedPublicBathroom ? bathroom.longitude : bathroom.address.longitude,
        directions,
        unisex,
        changing_table,
        accessible,
      } = bathroom;

      navigate(`/bathroom/${id}`, {
        state: { id, name, street, city, state, latitude, longitude, directions, unisex, changing_table, accessible },
      });
    }
  };

  const handleLooAroundClick = () => {
    setLoading(true);
    setErrorMessage('');
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const { latitude, longitude } = position.coords;
          handleLooAround(latitude, longitude);
        },
        err => {
          console.err('Error getting location:', err);
          setErrorMessage('Unable to retrieve your location. Please enable location services and try again.');
          setLoading(false);
        }
      );
    } else {
      setLoading(false);
      setErrorMessage('Geolocation is not supported by this browser.');
    }
  };

  const handleGoNow = () => {
    if (selectedPublicBathroom || selectedDbBathroom) {
      const bathroom = selectedPublicBathroom || selectedDbBathroom;
      const lat = selectedPublicBathroom ? bathroom.latitude : bathroom.address.latitude;
      const lng = selectedPublicBathroom ? bathroom.longitude : bathroom.address.longitude;
      window.open(`https://www.google.com/maps/dir/?api=1&destination=${lat},${lng}`, '_blank');
    }
  };

  if (!apiKey) {
    return <p>Error: Google Maps API key is missing.</p>;
  }

  return (
    <APIProvider apiKey={apiKey}>
      <div style={{ height: '100vh', width: '100%' }}>
        <button
          className={`flex justify-center items-center h-10 w-full ${loading ? 'opacity-50 cursor-not-allowed' : ''}`}
          onClick={handleLooAroundClick}
          disabled={loading}
        >
          {loading ? (
            <div className='flex gap-2 items-center'>
              <p>Loading</p>
              <div className='spinner h-4'></div>
            </div>
          ) : (
            'Refresh'
          )}
        </button>
        {errorMessage && <p className='text-red-500'>{errorMessage}</p>}
        <Map
          mapId={import.meta.env.VITE_MAP_ID_KEY}
          defaultZoom={14}
          defaultCenter={position}
          fullscreenControl={false}
          styles={mapConfiguration.option.styles}
        >
          <AdvancedMarker
            position={position}
            onClick={() => {
              setOpenPublicInfoWindow(true);
              setSelectedPublicBathroom({ name: 'Your Location' });
              setOpenDbInfoWindow(false);
            }}
          >
            <Pin scale={1.5} />
          </AdvancedMarker>

          {bathrooms.map(bathroom => {
            const bathroomPosition = { lat: bathroom.latitude, lng: bathroom.longitude };

            return (
              <AdvancedMarker
                key={bathroom.id}
                position={bathroomPosition}
                onClick={() => {
                  setOpenPublicInfoWindow(true);
                  setSelectedPublicBathroom(bathroom);
                  setOpenDbInfoWindow(false);
                }}
              >
                <div className='bg-[#FCD900] flex items-center justify-center' style={{ width: '40px', height: '40px', borderRadius: '50%' }}>
                  <span className='text-3xl'>💩</span>
                </div>
              </AdvancedMarker>
            );
          })}

          {dbBathrooms.map(bathroom => {
            const bathroomPosition = { lat: bathroom.address.latitude, lng: bathroom.address.longitude };

            return (
              <AdvancedMarker
                key={bathroom.bathroomId}
                position={bathroomPosition}
                onClick={() => {
                  setOpenDbInfoWindow(true);
                  setSelectedDbBathroom(bathroom);
                  setOpenPublicInfoWindow(false);
                }}
              >
                <div className='bg-[#FCD900] flex items-center justify-center' style={{ width: '40px', height: '40px', borderRadius: '50%' }}>
                  <span className='text-3xl'>💩</span>
                </div>
              </AdvancedMarker>
            );
          })}

          {openPublicInfoWindow && selectedPublicBathroom && (
            <InfoWindow
              className='min-w-[150px]'
              position={{ lat: selectedPublicBathroom.latitude, lng: selectedPublicBathroom.longitude }}
              onCloseClick={() => {
                setOpenPublicInfoWindow(false);
                setSelectedPublicBathroom(null);
              }}
            >
              <div className='text-gray-700 text-sm'>
                <h3 className='text-lg font-bold'>{selectedPublicBathroom.name}</h3>
                <p>{selectedPublicBathroom.street || 'No street info'}</p>
                <p className='text-blue-600 cursor-pointer' onClick={handleNavigate}>
                  More Details
                </p>
                <button className='mt-2 px-4 py-2 bg-[#747FFF] text-white rounded w-full' onClick={handleGoNow}>
                  Go Now
                </button>
              </div>
            </InfoWindow>
          )}

          {openDbInfoWindow && selectedDbBathroom && (
            <InfoWindow
              className='min-w-[150px]'
              position={{ lat: selectedDbBathroom.address.latitude, lng: selectedDbBathroom.address.longitude }}
              onCloseClick={() => {
                setOpenDbInfoWindow(false);
                setSelectedDbBathroom(null);
              }}
            >
              <div className='text-gray-700 text-sm'>
                <h3 className='text-lg font-bold'>{selectedDbBathroom.name}</h3>
                <p>{selectedDbBathroom.address.street || 'No street info'}</p>
                <p className='text-blue-600 cursor-pointer' onClick={handleNavigate}>
                  More Details
                </p>
                <button className='mt-2 px-4 py-2 bg-[#747FFF] text-white rounded w-full' onClick={handleGoNow}>
                  Go Now
                </button>
              </div>
            </InfoWindow>
          )}
        </Map>
      </div>
    </APIProvider>
  );
};

export default GoogleMap;
