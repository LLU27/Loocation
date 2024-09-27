package learn.loocation.controllers;

import learn.loocation.domain.RatingService;
import learn.loocation.domain.Result;
import learn.loocation.models.Rating;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @GetMapping("/bathroom/{bathroomId}")
    public List<Rating> findRatingsByBathroomId(@PathVariable int bathroomId) {
        return service.findRatingsByBathroomId(bathroomId);
    }

    @GetMapping("/user/{userId}")
    public List<Rating> findRatingsByUserId(@PathVariable int userId) {
        return service.findRatingsByUserId(userId);
    }

    @GetMapping("/{ratingId}")
    public Rating findRatingById(@PathVariable int ratingId) {
        return service.findRatingById(ratingId);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addRating(@RequestBody Rating rating) {
        Result<Rating> result = service.addRating(rating);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Object> updateRating(@PathVariable int ratingId, @RequestBody Rating rating) {
        if(ratingId != rating.getRatingId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Rating> result = service.updateRating(rating);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}