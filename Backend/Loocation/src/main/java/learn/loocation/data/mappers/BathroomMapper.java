package learn.loocation.data.mappers;

import learn.loocation.models.Address;
import learn.loocation.models.Bathroom;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BathroomMapper implements RowMapper<Bathroom> {
    public Bathroom mapRow(ResultSet rs, int rowNum) throws SQLException {

        Address address = new Address();
        address.setAddressId(rs.getInt("address_id"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setPostalCode(rs.getString("postal_code"));
        address.setLatitude(rs.getDouble("latitude"));
        address.setLongitude(rs.getDouble("longitude"));


        Bathroom bathroom = new Bathroom();
        bathroom.setBathroomId(rs.getInt("bathroom_id"));
        bathroom.setName(rs.getString("name"));
        bathroom.setAccessibility(rs.getBoolean("accessibility"));
        bathroom.setChangingStation(rs.getBoolean("changing_station"));
        bathroom.setUnisex(rs.getBoolean("unisex"));
        bathroom.setAddress(address);
        return bathroom;

    }
}