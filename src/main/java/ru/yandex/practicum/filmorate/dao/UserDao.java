package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> update(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(long id);

    void deleteUser(long id);

    List<User> getUsersByIds(List<Long> ids);

}