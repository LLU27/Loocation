package learn.loocation.domain;

import learn.loocation.data.RatingRepository;
import learn.loocation.models.Bathroom;
import learn.loocation.models.Rating;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository repository;

    public RatingService(RatingRepository repository) {
        this.repository = repository;
    }

    public Rating findRatingById(int ratingId) {
        return repository.findByRatingId(ratingId);
    }

    public Result<Rating> addRating(Rating rating) {
        Result<Rating> result = validate(rating);
        if (!result.isSuccess()) {
            return result;
        }

        if (rating.getRatingId() != 0) {
            result.addMessage("ratingId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        rating = repository.addRating(rating);
        result.setPayload(rating);
        return result;
    }

    public Result<Rating> updateRating(Rating rating) {
        Result<Rating> result = validate(rating);
        if (!result.isSuccess()) {
            return result;
        }

        if (rating.getRatingId() <= 0) {
            result.addMessage("ratingId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.updateRating(rating)) {
            String msg = String.format("ratingId: %s, not found", rating.getRatingId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Rating> validate(Rating rating) {
        Result<Rating> result = new Result<>();

        if (rating == null) {
            result.addMessage("rating cannot be null", ResultType.INVALID);
            return result;
        }
        if(rating.getRating() > 5 || rating.getRating() < 1) {
            result.addMessage("rating must be between 1 and 5", ResultType.INVALID);
        }

        return result;
    }
}