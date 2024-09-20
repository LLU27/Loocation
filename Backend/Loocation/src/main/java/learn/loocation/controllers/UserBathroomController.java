package learn.loocation.controllers;

import learn.loocation.domain.Result;
import learn.loocation.domain.UserBathroomService;
import learn.loocation.models.UserBathroom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userbathroom")
public class UserBathroomController {

    private final UserBathroomService service;

    public UserBathroomController(UserBathroomService service) {
        this.service = service;
    }

    @PostMapping("/add/bathroomId/{bathroomId}/userId/{userId}")
    public ResponseEntity<Object> add(@RequestBody UserBathroom userBathroom) {
        Result<UserBathroom> result = service.addUserBathroom(userBathroom);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> findByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(service.findBathroomsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/bathroom/{bathroomId}")
    public ResponseEntity<Object> findByBathroomId(@PathVariable int bathroomId) {
        return new ResponseEntity<>(service.findUsersByBathroomId(bathroomId), HttpStatus.OK);
    }

}