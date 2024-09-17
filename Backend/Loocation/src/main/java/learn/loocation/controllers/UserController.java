package learn.loocation.controllers;

import learn.loocation.domain.Result;
import learn.loocation.domain.UserService;
import learn.loocation.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAllUser() {
        return service.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User findUserById(int userId) {
        return service.findUserById(userId);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        Result<User> result = service.addUser(user);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
}