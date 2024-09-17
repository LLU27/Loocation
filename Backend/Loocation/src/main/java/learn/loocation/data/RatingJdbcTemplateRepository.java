package learn.loocation.data;

import learn.loocation.data.mappers.RatingMapper;
import learn.loocation.models.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;

@Repository
public class RatingJdbcTemplateRepository implements RatingRepository {
    private final JdbcTemplate jdbcTemplate;

    public RatingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rating findByRatingId(int ratingId) {
        final String sql = "select * from rating where rating_id = ?;";
        return jdbcTemplate.query(sql, new RatingMapper(), ratingId)
                .stream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public Rating addRating(Rating rating) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("rating")
                .usingGeneratedKeyColumns("rating_id");

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", rating.getUserId());
        parameters.put("bathroom_id", rating.getBathroomId());
        parameters.put("rating", rating.getRating());
        parameters.put("comment", rating.getComment());

      int ratingId = insert.executeAndReturnKey(parameters).intValue();
        rating.setRatingId(ratingId);

        return rating;
    }

    @Override
    public boolean updateRating(Rating rating) {
        final String sql = """
                update rating set rating =?,comments =? where rating_id = ?;
                """;
        return jdbcTemplate.update(sql, rating.getRating(), rating.getComment(), rating.getRatingId()) > 0;
    }

}