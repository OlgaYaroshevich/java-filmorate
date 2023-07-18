package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDao {
    User create(User user);

    Optional<User> update(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(long id);

    void deleteUser(long id);

    List<User> getUsersByIds(Set<Long> ids);

}