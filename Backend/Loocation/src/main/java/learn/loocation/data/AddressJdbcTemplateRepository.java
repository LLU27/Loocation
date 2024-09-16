package learn.loocation.data;

import learn.loocation.models.Address;

public class AddressJdbcTemplateRepository implements AddressRepository {
    @Override
    public Address findById(int addressId) {
        return null;
    }

    @Override
    public Address add(Address address) {
        return null;
    }

    @Override
    public boolean update(Address address) {
        return false;
    }

    @Override
    public boolean deleteById(int addressId) {
        return false;
    }
}