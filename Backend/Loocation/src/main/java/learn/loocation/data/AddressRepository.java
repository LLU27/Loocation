package learn.loocation.data;

import learn.loocation.models.Address;

public interface AddressRepository {
    Address findById(int addressId);

    Address add(Address address);

    boolean update(Address address);

    boolean deleteById(int addressId);
}