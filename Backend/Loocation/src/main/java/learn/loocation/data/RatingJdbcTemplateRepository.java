package learn.loocation.data;

import learn.loocation.data.mappers.RatingMapper;
import learn.loocation.models.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;

public class RatingJdbcTemplateRepository implements RatingRepository {
    private final JdbcTemplate jdbcTemplate;
    public RatingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rating findById(int ratingId) {
        final String sql = "select * from rating where rating_id = ?";
        return jdbcTemplate.query(sql, new RatingMapper(), ratingId)
                .stream()
                .findFirst()
                .orElse(null);
    }
    @Override
    public Rating add(Rating rating) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("rating")
                .usingGeneratedKeyColumns("rating_id");
        HashMap<String,Object> args = new HashMap<>();
        int ratingId = insert.executeAndReturnKey(args)
                .intValue();
        rating.setRatingId(ratingId);
        return rating;
    }

    @Override
    public boolean update(Rating rating) {
        final String sql = """
                update rating set rating =?,comments =? where rating_id = ?;
                """;
        return jdbcTemplate.update(sql,rating.getRating(),rating.getComment(),rating.getRatingId())>0;
    }

}