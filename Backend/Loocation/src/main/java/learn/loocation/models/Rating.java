package learn.loocation.models;

import java.util.Objects;

public class Rating {
    private int ratingId;
    private int bathroomId;
    private int userId;
    private int rating_value;
    private String comment;

    public Rating() {
    }

    public Rating(int ratingId, int bathroomId, int userId, int rating_value, String comment) {
        this.ratingId = ratingId;
        this.bathroomId = bathroomId;
        this.userId = userId;
        this.rating_value = rating_value;
        this.comment = comment;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getBathroomId() {
        return bathroomId;
    }

    public void setBathroomId(int bathroomId) {
        this.bathroomId = bathroomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating_value() {
        return rating_value;
    }

    public void setRating_value(int rating_value) {
        this.rating_value = rating_value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return ratingId == rating.ratingId && bathroomId == rating.bathroomId && userId == rating.userId && rating_value == rating.rating_value && Objects.equals(comment, rating.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingId, bathroomId, userId, rating_value, comment);
    }
}