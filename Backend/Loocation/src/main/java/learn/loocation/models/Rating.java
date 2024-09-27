package learn.loocation.models;

import java.util.Objects;

public class Rating {
    private int ratingId;
    private int bathroomId;
    private int userId;
    private int rating;
    private String comment;
    

    public Rating() {
    }

    public Rating(int ratingId, int bathroomId, int userId, int rating, String comment) {
        this.ratingId = ratingId;
        this.bathroomId = bathroomId;
        this.userId = userId;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
        Rating rating1 = (Rating) o;
        return ratingId == rating1.ratingId && bathroomId == rating1.bathroomId && userId == rating1.userId && rating == rating1.rating && Objects.equals(comment, rating1.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingId, bathroomId, userId, rating, comment);
    }
}