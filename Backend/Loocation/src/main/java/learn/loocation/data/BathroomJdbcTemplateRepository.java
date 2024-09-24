package learn.loocation.data;

import learn.loocation.data.mappers.BathroomMapper;
import learn.loocation.models.Bathroom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public class BathroomJdbcTemplateRepository implements BathroomRepository {

    private final JdbcTemplate jdbcTemplate;

    public BathroomJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<Bathroom> findAllBathrooms() {
        final String sql = """
            select b.bathroom_id, b.name, b.accessibility, b.changing_station, b.unisex, a.address_id, a.street, a.city, a.state, a.latitude, a.longitude
            from bathroom b
            inner join address a on b.address_id = a.address_id
            """;
        return jdbcTemplate.query(sql, new BathroomMapper());
    }

    @Override
    public Bathroom findBathroomById(int bathroomId) {
        final String sql = """
            select b.bathroom_id, b.name, b.accessibility, b.changing_station, b.unisex, a.address_id, a.street, a.city, a.state,  a.latitude, a.longitude 
            from bathroom b
            inner join address a on b.address_id = a.address_id
            where b.bathroom_id = ?;
            """;
        return jdbcTemplate.query(sql, new BathroomMapper(), bathroomId).stream().findFirst().orElse(null);
    }

    @Override
    public Bathroom findByAddressId(int addressId) {
        final String sql = """
            select b.bathroom_id, b.name, b.accessibility, b.changing_station, b.unisex, a.address_id, a.street, a.city, a.state, a.latitude, a.longitude
            from bathroom b
            inner join address a on b.address_id = a.address_id
            where a.address_id = ?;
            """;
        return jdbcTemplate.query(sql, new BathroomMapper(), addressId).stream().findFirst().orElse(null);
    }

    @Override
    public Bathroom addBathroom(Bathroom bathroom) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("bathroom").usingGeneratedKeyColumns("bathroom_id");

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", bathroom.getName());
        parameters.put("accessibility", bathroom.hasAccessibility());
        parameters.put("changing_station", bathroom.hasChangingStation());
        parameters.put("unisex", bathroom.isUnisex());
        parameters.put("address_id", bathroom.getAddress().getAddressId());

        int bathroomId = insert.executeAndReturnKey(parameters).intValue();
        bathroom.setBathroomId(bathroomId);
        return bathroom;
    }

    @Override
    public boolean updateBathroom(Bathroom bathroom) {

        final String sql = "update bathroom set name = ?, accessibility = ?, changing_station = ?, unisex = ? where bathroom_id = ?;";
        return jdbcTemplate.update(sql, bathroom.getName(), bathroom.hasAccessibility(), bathroom.hasChangingStation(), bathroom.isUnisex(), bathroom.getBathroomId()) > 0;
    }

    @Override
    public boolean deleteBathroomById(int bathroomId) {
        final String deleteUserBathroomSql = "delete from user_bathroom where bathroom_id = ?";
        final String deleteRatingsSql = "delete from rating where bathroom_id = ?";
        final String deleteBathroomSql = "delete from bathroom where bathroom_id = ?";
        jdbcTemplate.update(deleteUserBathroomSql, bathroomId);
        jdbcTemplate.update(deleteRatingsSql, bathroomId);
        int rowsAffected = jdbcTemplate.update(deleteBathroomSql, bathroomId);

        return rowsAffected > 0;
    }
}