package learn.loocation.domain;

import learn.loocation.data.BathroomRepository;
import learn.loocation.models.Address;
import learn.loocation.models.Bathroom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BathroomService {

    private final BathroomRepository repository;

    public BathroomService(BathroomRepository repository) {
        this.repository = repository;
    }

    public List<Bathroom> findAllBathrooms() {
        return repository.findAllBathrooms();
    }

    public Bathroom findBathroomById(int bathroomId) {
        return repository.findBathroomById(bathroomId);
    }

    public Result<Bathroom> add(Bathroom bathroom) {
        Result<Bathroom> result = validate(bathroom);
        if (!result.isSuccess()) {
            return result;
        }

        if (bathroom.getBathroomId() != 0) {
            result.addMessage("bathroomId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        bathroom = repository.addBathroom(bathroom);
        result.setPayload(bathroom);
        return result;
    }

    public Result<Bathroom> update(Bathroom bathroom) {
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