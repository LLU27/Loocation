package learn.loocation.data;

import learn.loocation.models.Rating;

public interface RatingRepository {
    Rating addRating(Rating rating);

    boolean updateRating(Rating rating);

    Rating findByRatingId(int ratingId);

}