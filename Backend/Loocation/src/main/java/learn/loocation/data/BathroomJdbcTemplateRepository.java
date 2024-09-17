package learn.loocation.data;

import learn.loocation.data.mappers.BathroomMapper;
import learn.loocation.models.Bathroom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
@Repository
public class BathroomJdbcTemplateRepository implements BathroomRepository {

    private final JdbcTemplate jdbcTemplate;
    public BathroomJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bathroom> findAllBathrooms() {
        final String sql = "select * from bathroom;";
        return jdbcTemplate.query(sql, new BathroomMapper());
    }

    @Override
    public Bathroom findBathroomById(int bathroomId) {
        final String sql = "select * from bathroom where bathroom_id = ?;";
        return jdbcTemplate.query(sql, new BathroomMapper(), bathroomId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Bathroom addBathroom(Bathroom bathroom) {
        final String sql = "insert into bathroom (name, accessibility, changing_station, unisex) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, bathroom.getName());
            ps.setBoolean(2, bathroom.isAccessibility());
            ps.setBoolean(3, bathroom.isChanging_station());
            ps.setBoolean(4, bathroom.isUnisex());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        bathroom.setBathroomId(keyHolder.getKey().intValue());
        return bathroom;
    }

    @Override
    public boolean updateBathroom(Bathroom bathroom) {

        final String sql = "update bathroom set name = ?, accessibility = ?, changing_station = ?, unisex = ? where bathroom_id = ?;";
        return jdbcTemplate.update(sql, bathroom.getName(), bathroom.isAccessibility(), bathroom.isChanging_station(), bathroom.isUnisex(), bathroom.getBathroomId()) > 0;
    }

    @Override
    public boolean deleteBathroomById(int bathroomId) {
        final String sql = "delete from bathroom where bathroom_id = ?;";
        return jdbcTemplate.update(sql, bathroomId) > 0;
    }
}