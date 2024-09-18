package learn.loocation.domain;

import learn.loocation.data.UserBathroomRepository;
import learn.loocation.models.UserBathroom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBathroomService {

    private final UserBathroomRepository repository;

    public UserBathroomService(UserBathroomRepository repository) {
        this.repository = repository;
    }

    public Result<UserBathroom> addUserBathroom(UserBathroom userBathroom) {
        Result<UserBathroom> result = validate(userBathroom);
        if (!result.isSuccess()) {
            return result;
        }

        List<UserBathroom> existing = repository.findByUserId(userBathroom.getUserId());
        for (UserBathroom ub : existing) {
            if (ub.getBathroomId() == userBathroom.getBathroomId()) {
                result.addMessage("User has already added this bathroom", ResultType.DUPLICATE);
                return result;
            }
        }

        boolean success = repository.add(userBathroom);
        if (success) {
            result.setPayload(userBathroom);
        } else {
            result.addMessage("Failed to add user-bathroom relationship", ResultType.INVALID);
        }

        return result;
    }

    public List<UserBathroom> findBathroomsByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public List<UserBathroom> findUsersByBathroomId(int bathroomId) {
        return repository.findByBathroomId(bathroomId);
    }

    private Result<UserBathroom> validate(UserBathroom userBathroom) {
        Result<UserBathroom> result = new Result<>();

        if (userBathroom == null) {
            result.addMessage("UserBathroom cannot be null", ResultType.INVALID);
            return result;
        }

        if (userBathroom.getUserId() <= 0) {
            result.addMessage("Valid userId is required", ResultType.INVALID);
        }

        if (userBathroom.getBathroomId() <= 0) {
            result.addMessage("Valid bathroomId is required", ResultType.INVALID);
        }

        return result;
    }
}