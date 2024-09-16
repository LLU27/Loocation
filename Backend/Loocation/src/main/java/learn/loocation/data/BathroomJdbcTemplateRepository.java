package learn.loocation.data;

import learn.loocation.data.mappers.BathroomMapper;
import learn.loocation.models.Bathroom;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BathroomJdbcTemplateRepository implements BathroomRepository {

    private final JdbcTemplate jdbcTemplate;
    public BathroomJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bathroom> findAll() {
        final String sql = "select * from bathroom;";
        return jdbcTemplate.query(sql, new BathroomMapper());
    }

    @Override
    public Bathroom findById(int bathroomId) {
        final String sql = "select * from bathroom where bathroom_id = ?;";
        return jdbcTemplate.query(sql, new BathroomMapper(), bathroomId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Bathroom add(Bathroom bathroom) {
        return null;
    }

    @Override
    public boolean update(Bathroom bathroom) {
        return false;
    }

    @Override
    public boolean deleteById(int bathroomId) {
        return false;
    }
}