package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public UserService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public User addNewUser(User user) {
        return userStorage.addNewUser(user);
    }

    public User updateUser(User user) throws ValidationException {
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(long id) throws ValidationException {
        return userStorage.getUser(id);
    }

    public void addFriend(long userId, long friendId) throws ValidationException {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.setFriendsId(friend.getId());
        friend.setFriendsId(user.getId());
    }

    public void delFriend(long userId, long friendId) throws ValidationException {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.getFriendsId().remove(friend.getId());
        friend.getFriendsId().remove(user.getId());
    }

    public Collection<User> getUserFriends(long id) throws ValidationException {
        User userFriends = userStorage.getUser(id);
        return userStorage.getUsersByIds(userFriends.getFriendsId());
    }

    public Collection<User> getCommonFriends(long userId, long otherId) throws ValidationException {
        User user1 = userStorage.getUser(userId);
        User user2 = userStorage.getUser(otherId);
        Set<Long> commonFriendIds = findCommonElements(user1.getFriendsId(), user2.getFriendsId());
        return userStorage.getUsersByIds(commonFriendIds);
    }

    private static <T> Set<T> findCommonElements(Collection<T> first, Collection<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }

    public void delUser(long id) {
        userStorage.delUser(id);
    }
}
