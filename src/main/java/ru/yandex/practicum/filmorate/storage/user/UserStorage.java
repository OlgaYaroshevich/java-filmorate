package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    User addNewUser(User user);

    User updateUser(User user) throws ValidationException;

    User getUser(long id) throws ValidationException;

    List<User> getUsers();

    List<User> getUsersByIds(Collection<Long> ids) throws ValidationException;

    void deleteUser(long id);
}
