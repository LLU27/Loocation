import GoogleMap from '../components/GoogleMap';

const MapView = ({ bathrooms, coords }) => {
  return <div>{coords ? <GoogleMap lat={coords.lat} long={coords.lng} bathrooms={bathrooms} /> : <p>Coordinates not available</p>}</div>;
};

export default MapView;
