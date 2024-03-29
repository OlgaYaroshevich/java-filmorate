package ru.yandex.practicum.filmorate.storage.dbStorage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    Optional<User> update(User user);

    List<User> getUsers();

    Optional<User> getUserById(long id);

    List<User> getUsersByIds(List<Long> ids);

    void deleteUser(long id);
}