package learn.loocation.data;

import learn.loocation.models.Rating;

public interface RatingRepository {
    Rating add(Rating rating);

    boolean update(Rating rating);

    Rating findById(int ratingId);

}