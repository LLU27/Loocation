package learn.loocation.data.mappers;

import learn.loocation.models.UserBathroom;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBathroomMapper implements RowMapper<UserBathroom> {

    @Override
    public UserBathroom mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserBathroom userBathroom = new UserBathroom();
        userBathroom.setUserId(resultSet.getInt("user_id"));
        userBathroom.setBathroomId(resultSet.getInt("bathroom_id"));
        return userBathroom;
    }
}