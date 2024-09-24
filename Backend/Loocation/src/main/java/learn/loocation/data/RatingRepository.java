package learn.loocation.data;

import learn.loocation.models.Rating;

import java.util.List;

public interface RatingRepository {
    Rating addRating(Rating rating);

    boolean updateRating(Rating rating);

    Rating findByRatingId(int ratingId);

    List<Rating> findRatingsByBathroomId(int bathroomId);

}