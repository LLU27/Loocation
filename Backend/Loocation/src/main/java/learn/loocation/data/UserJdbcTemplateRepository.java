package learn.loocation.data;

import learn.loocation.data.mappers.UserMapper;
import learn.loocation.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;

public class UserJdbcTemplateRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "select * from user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(int userId) {
        final String sql = "select * from user where user_id = ?";
        return jdbcTemplate.query(sql, new UserMapper(), userId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public User add(User user) {
        final String sql = "insert into user (username, email, password) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }
}