package learn.loocation.data;

import learn.loocation.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(int userId);

    User add(User user);
}