package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(value = "/users")
    public User addNewUser(@Valid @RequestBody User user) throws ValidationException {
        return userService.addNewUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        return userService.updateUser(user);
    }

    @GetMapping("/users/{id}")
    public User geleteUser(@PathVariable("id") long id) throws ValidationException {
        return userService.getUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") long userId,
                          @PathVariable("friendId") long friendId) throws ValidationException {
        userService.addFriend(userId, friendId);
        log.debug("Друг добавлен" + friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") long userId,
                             @PathVariable("friendId") long friendId) throws ValidationException {
        userService.deleteFriend(userId, friendId);
        log.debug("Друг удален.");
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable("id") long id) throws ValidationException {
        return userService.getUserFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") long userId, @PathVariable("otherId") long otherId) throws ValidationException {
        return userService.getCommonFriends(userId, otherId);
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}
