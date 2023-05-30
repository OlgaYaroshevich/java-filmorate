package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Repository
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> usersMap = new HashMap<>();
    long id = 0L;

    private Long countId() {
        return ++id;
    }

    @Override
    public User addNewUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(countId());
        usersMap.put(user.getId(), user);
        log.debug("Добавлен пользователь {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (usersMap.containsKey(user.getId())) {
            usersMap.put(user.getId(), user);
            log.debug("Обновлен пользователь {}", user);
            return user;
        } else {
            throw new UserNotFoundException("Такого id не существует");
        }
    }

    @Override
    public User getUser(long id) {
        if (usersMap.containsKey(id)) {
            return usersMap.get(id);
        }
        throw new UserNotFoundException("Такого id не существует");
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(usersMap.values());
    }

    public Collection<User> getUsersByIds(Collection<Long> ids) {
        List<User> result = new ArrayList<>();
        for (long userID : ids) {
            if (!usersMap.containsKey(userID)) {
                throw new UserNotFoundException("Такого id не существует");
            }
            result.add(usersMap.get(userID));
        }
        return result;
    }

    public void delUser(long id) {
        log.debug("Удаление пользователя {}", id);
        usersMap.remove(id);
    }
}
