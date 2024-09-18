package learn.loocation.domain;

import learn.loocation.data.AddressRepository;
import learn.loocation.models.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address findAddressById(int addressId) {
        return repository.findAddressById(addressId);
    }

    public Result<Address> addAddress(Address address) {
        Result<Address> result = validate(address);
        if (!result.isSuccess()) {
            return result;
        }
        if (address.getAddressId() != 0) {
            result.addMessage("addressId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }
        address = repository.addAddress(address);
        result.setPayload(address);
        return result;
    }

    public Result<Address> updateAddress(Address address) {
        Result<Address> result = validate(address);
        if (!result.isSuccess()) {
            return result;
        }
        if (address.getAddressId() <= 0) {
            result.addMessage("addressId must be set for `update` operation", ResultType.INVALID);
            return result;
        }
        if (!repository.updateAddress(address)) {
            String msg = String.format("addressId: %s, not found", address.getAddressId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Boolean> deleteAddressById(int addressId) {
        Result<Boolean> result = new Result<>();
        if (addressId <= 0) {
            result.addMessage("addressId must be greater than 0", ResultType.INVALID);
            result.setPayload(false);
            return result;
        }
        boolean success = repository.deleteAddressById(addressId);
        if (!success) {
            result.addMessage(String.format("addressId: %s, not found", addressId), ResultType.NOT_FOUND);
        }
        result.setPayload(success);
        return result;
    }

    private Result<Address> validate(Address address) {
        Result<Address> result = new Result<>();

        if (address == null) {
            result.addMessage("address cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(address.getStreet())) {
            result.addMessage("Street is required", ResultType.INVALID);
        }


        if (Validations.isNullOrBlank(address.getCity())) {
            result.addMessage("City is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(address.getState())) {
            result.addMessage("State is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(address.getZipCode())) {
            result.addMessage("Postal Code is required", ResultType.INVALID);
        }

        return result;
    }
}