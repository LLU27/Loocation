package learn.loocation.data.mappers;

import learn.loocation.models.Rating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<Rating> {
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rating rating = new Rating();
        rating.setRatingId(rs.getInt("rating_id"));
        rating.setBathroomId(rs.getInt("bathroom_id"));
        rating.setUserId(rs.getInt("user_id"));
        rating.setRating(rs.getInt("rating_value"));
        rating.setComment(rs.getString("comment"));
        return rating;
    }
}