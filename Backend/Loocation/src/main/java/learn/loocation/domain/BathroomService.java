package learn.loocation.domain;

import learn.loocation.data.AddressRepository;
import learn.loocation.data.BathroomRepository;
import learn.loocation.models.Address;
import learn.loocation.models.Bathroom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BathroomService {

    private final BathroomRepository repository;
    private final AddressRepository addressRepository;

    public BathroomService(BathroomRepository repository,
                           AddressRepository addressRepository, AddressRepository addressRepository1) {
        this.repository = repository;
        this.addressRepository = addressRepository1;
    }

    public List<Bathroom> findAllBathrooms() {
        return repository.findAllBathrooms();
    }

    public Bathroom findBathroomById(int bathroomId) {
        return repository.findBathroomById(bathroomId);
    }

    public Bathroom findByAddressId(int addressId) {
        return repository.findByAddressId(addressId);
    }

    public Result<Bathroom> addBathroom(Bathroom bathroom) {
        Result<Bathroom> result = validate(bathroom);
        if (!result.isSuccess()) {
            return result;
        }

        Bathroom existingBathroom = repository.findByAddressId(bathroom.getAddress().getAddressId());
        if (existingBathroom != null) {
            result.setPayload(existingBathroom);
            return result;
        }

        if (bathroom.getBathroomId() != 0) {
            result.addMessage("bathroomId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        Address address = bathroom.getAddress();
        Address existingAddress = addressRepository.findByLatLong(address.getLatitude(), address.getLongitude());

        if (existingAddress != null) {
            bathroom.setAddress(existingAddress);
        } else {
            Address addedAddress = addressRepository.addAddress(address);
            bathroom.setAddress(addedAddress);
        }

        bathroom = repository.addBathroom(bathroom);
        result.setPayload(bathroom);
        return result;
    }

    public Result<Bathroom> updateBathroom(Bathroom bathroom) {
        Result<Bathroom> result = validate(bathroom);
        if (!result.isSuccess()) {
            return result;
        }

        if (bathroom.getBathroomId() <= 0) {
            result.addMessage("bathroomId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateBathroom(bathroom)) {
            String msg = String.format("bathroomId: %s, not found", bathroom.getBathroomId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Boolean> deleteBathroomById(int bathroomId) {
        Result<Boolean> result = new Result<>();
        if (bathroomId <= 0) {
            result.addMessage("bathroomId must be greater than 0", ResultType.INVALID);
            result.setPayload(false);
            return result;
        }
        boolean success = repository.deleteBathroomById(bathroomId);
        if (!success) {
            result.addMessage(String.format("bathroomId: %s, not found", bathroomId), ResultType.NOT_FOUND);
        }
        result.setPayload(success);
        return result;

    }

    private Result<Bathroom> validate(Bathroom bathroom) {
        Result<Bathroom> result = new Result<>();

        if (bathroom == null) {
            result.addMessage("bathroom cannot be null", ResultType.INVALID);
            return result;
        }
        if(Validations.isNullOrBlank(bathroom.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }
        return result;
    }
}