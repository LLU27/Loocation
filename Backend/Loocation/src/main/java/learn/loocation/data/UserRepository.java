package learn.loocation.data;

import learn.loocation.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAllUsers();

    User findUserById(int userId);

    User addUser(User user);

    User findUserByEmail(String email);
}