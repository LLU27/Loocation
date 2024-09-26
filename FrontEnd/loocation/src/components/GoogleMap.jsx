import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { APIProvider, Map, AdvancedMarker, Pin, InfoWindow } from '@vis.gl/react-google-maps';
import logo from '../assets/logo.png';

const GoogleMap = ({ lat, long, bathrooms }) => {
  const [open, setOpen] = useState(false);
  const [selectedBathroom, setSelectedBathroom] = useState(null);
  const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;
  const position = { lat, lng: long };
  const navigate = useNavigate();

  const handleNavigate = () => {
    if (selectedBathroom) {
      const { id, name, address, latitude, longitude, street, city, state, directions, unisex, changing_table, accessible } = selectedBathroom;

      navigate(`/bathroom/${id}`, {
        state: { id, name, address, latitude, longitude, street, city, state, directions, unisex, changing_table, accessible },
      });
    }
  };

  if (!apiKey) {
    return <p>Error: Google Maps API key is missing.</p>;
  }

  return (
    <APIProvider apiKey={apiKey}>
      <div style={{ height: '100vh', width: '100%' }}>
        <Map mapId={import.meta.env.VITE_MAP_ID_KEY} defaultZoom={16} defaultCenter={position}>
          <AdvancedMarker
            position={position}
            onClick={() => {
              setOpen(true);
              setSelectedBathroom({ name: 'Your Location' });
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
                  setOpen(true);
                  setSelectedBathroom(bathroom);
                }}
              >
                <div className=' bg-[#FCD900]'>
                  <span className='text-3xl'>💩</span>
                </div>
              </AdvancedMarker>
            );
          })}

          {open && selectedBathroom && (
            <InfoWindow
              position={selectedBathroom ? { lat: selectedBathroom.latitude, lng: selectedBathroom.longitude } : position}
              onCloseClick={() => {
                setOpen(false);
                setSelectedBathroom(null);
              }}
            >
              <div className='text-gray-700 text-sm'>
                <h3 className='text-lg font-bold'>{selectedBathroom.name}</h3>
                <p>{selectedBathroom.street}</p>
                <p to={`/bathroom/${selectedBathroom.id}`} className='text-blue-600' onClick={handleNavigate}>
                  More Details
                </p>
              </div>
            </InfoWindow>
          )}
        </Map>
      </div>
    </APIProvider>
  );
};

export default GoogleMap;
