package learn.loocation.data;

import learn.loocation.models.UserBathroom;

import java.util.List;

public interface UserBathroomRepository {

    boolean add (UserBathroom userBathroom);

    List<UserBathroom> findByUserId(int userId);

    List<UserBathroom> findByBathroomId(int bathroomId);
}