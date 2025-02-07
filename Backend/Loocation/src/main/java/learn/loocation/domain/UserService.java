package learn.loocation.domain;

import learn.loocation.data.UserRepository;
import learn.loocation.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findUserById(int userId) {
        return repository.findUserById(userId);
    }

    public List<User> findAllUsers() {
        return repository.findAllUsers();
    }

    public Result<User> addUser(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("userId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        } http://localhost:5173/

        user = repository.addUser(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> loginUser(User user) {
        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }
        if(Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required", ResultType.INVALID);
        }
        if(Validations.isNullOrBlank(user.getPassword())) {
            result.addMessage("password is required", ResultType.INVALID);
        }
        User existing = repository.findUserByEmail(user.getEmail());
        if (existing == null) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }
        if (!existing.getPassword().equals(user.getPassword())) {
            result.addMessage("Password is incorrect", ResultType.INVALID);
            return result;
        }
        result.setPayload(existing);
        return result;
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }
        if(Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required", ResultType.INVALID);
        }
        if(Validations.isNullOrBlank(user.getPassword())) {
            result.addMessage("password is required", ResultType.INVALID);
        }
        if(Validations.isNullOrBlank(user.getUsername())) {
            result.addMessage("username is required", ResultType.INVALID);
        }
        return result;
    }


}