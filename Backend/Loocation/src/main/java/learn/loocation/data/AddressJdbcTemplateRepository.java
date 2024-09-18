package learn.loocation.data;

import learn.loocation.data.mappers.AddressMapper;
import learn.loocation.models.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AddressJdbcTemplateRepository implements AddressRepository {
    private final JdbcTemplate jdbcTemplate;

    public AddressJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address findAddressById(int addressId) {
        final String sql = "select * from address where address_id = ?";
        Address result = jdbcTemplate.query(sql, new AddressMapper(), addressId)
                .stream()
                .findFirst()
                .orElse(null);
        return result;
    }

    @Override
    public Address addAddress(Address address) {
        final String sql = "insert into address ( street, city, state, zipcode, latitude, longitude) values (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZipCode());
            ps.setDouble(5, address.getLatitude());
            ps.setDouble(6, address.getLongitude());
            return ps;
        },keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        address.setAddressId(keyHolder.getKey().intValue());
        return address;
    }

    @Override
    public boolean updateAddress(Address address) {
        final String sql = "update address set street = ?, city = ?, state = ?, zipcode = ?, latitude = ?, longitude = ? where address_id = ?";
        return jdbcTemplate.update(sql,
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getLatitude(),
                address.getLongitude(),
                address.getAddressId()) > 0;
    }

    @Override
    public boolean deleteAddressById(int addressId) {
        final String deleteUserBathroomSql = "delete from user_bathroom where bathroom_id = ?";
        final String deleteRatingsSql = "delete from rating where bathroom_id = ?";
        final String deleteBathroomSql = "delete from bathroom where bathroom_id = ?";
        final String deleteAddressSql = "delete from address where address_id = ?";
        jdbcTemplate.update(deleteUserBathroomSql, addressId);
        jdbcTemplate.update(deleteRatingsSql, addressId);
jdbcTemplate.update(deleteBathroomSql, addressId);
int rowsAffected = jdbcTemplate.update(deleteAddressSql, addressId);
return rowsAffected > 0;
    }
}