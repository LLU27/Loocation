package learn.loocation.data.mappers;

import learn.loocation.models.Bathroom;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BathroomMapper implements RowMapper<Bathroom> {
    public Bathroom mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bathroom bathroom = new Bathroom();
        bathroom.setBathroomId(rs.getInt("bathroom_id"));
        bathroom.setName(rs.getString("name"));
        bathroom.setAccessibility(rs.getBoolean("accessibility"));
        bathroom.setChanging_station(rs.getBoolean("changing_station"));
        bathroom.setUnisex(rs.getBoolean("unisex"));
        return bathroom;
    }
}