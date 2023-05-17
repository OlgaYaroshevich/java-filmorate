package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> usersMap = new HashMap<>();
    private int id = 1;

    @PostMapping()
    public User addNewUser(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(id++);
        usersMap.put(user.getId(), user);
        log.debug("Добавлен новый пользователь: " + user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        if (usersMap.get(user.getId()) == null) {
            log.error("Обновление несуществующего пользователя");
            throw new ValidationException();
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        usersMap.put(user.getId(), user);
        log.debug("Пользователь обновлен: " + user);
        return user;
    }

    @GetMapping()
    public Collection<User> getUsers() {
        log.debug("Получение пользователей");
        return usersMap.values();
    }
}
