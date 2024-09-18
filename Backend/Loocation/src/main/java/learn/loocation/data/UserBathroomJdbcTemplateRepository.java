package learn.loocation.data;

import learn.loocation.data.mappers.UserBathroomMapper;
import learn.loocation.models.UserBathroom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserBathroomJdbcTemplateRepository implements UserBathroomRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserBathroomJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(UserBathroom userBathroom) {
        final String sql = "insert into user_bathroom (user_id, bathroom_id) values (?, ?);";
        return jdbcTemplate.update(sql, userBathroom.getUserId(), userBathroom.getBathroomId()) > 0;

    }

    @Override
    public List<UserBathroom> findByUserId(int userId) {
        final String sql = "select user_id, bathroom_id from user_bathroom where user_id = ?;";
        return jdbcTemplate.query(sql, new UserBathroomMapper(), userId);
    }

    @Override
    public List<UserBathroom> findByBathroomId(int bathroomId) {
        final String sql = "select user_id, bathroom_id from user_bathroom where bathroom_id = ?;";
        return jdbcTemplate.query(sql, new UserBathroomMapper(), bathroomId);
    }
}