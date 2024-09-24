package learn.loocation.data;

import learn.loocation.models.Address;

public interface AddressRepository {
    Address findAddressById(int addressId);
    Address findByLatLong(double latitude, double longitude);

    Address addAddress(Address address);

    boolean updateAddress(Address address);

    boolean deleteAddressById(int addressId);

    Address findAddressByStreet(String street);
}