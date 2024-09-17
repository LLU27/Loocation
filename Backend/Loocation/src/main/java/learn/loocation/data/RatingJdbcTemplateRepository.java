package learn.loocation.data;

import learn.loocation.data.mappers.RatingMapper;
import learn.loocation.models.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

public class RatingJdbcTemplateRepository implements RatingRepository {
    private final JdbcTemplate jdbcTemplate;

    public RatingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rating findByRatingId(int ratingId) {
        final String sql = "select * from rating where rating_id = ?";
        return jdbcTemplate.query(sql, new RatingMapper(), ratingId)
                .stream()
                .findFirst()
                .orElse(null);
    }


    @Override
    public Rating addRating(Rating rating) {
        final String sql = "insert into rating (rating,comments) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rating.getRating());
            ps.setString(2, rating.getComment());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        rating.setRatingId(keyHolder.getKey().intValue());
        return rating;
    };

    @Override
    public boolean updateRating(Rating rating) {
        final String sql = """
                update rating set rating =?,comments =? where rating_id = ?;
                """;
        return jdbcTemplate.update(sql, rating.getRating(), rating.getComment(), rating.getRatingId()) > 0;
    }

}