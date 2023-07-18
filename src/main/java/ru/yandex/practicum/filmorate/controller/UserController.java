package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    private final FriendshipService friendshipService;

    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@Valid @PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@Valid @PathVariable long userId, @PathVariable long friendId) {
        friendshipService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void unfriend(@Valid @PathVariable long userId, @PathVariable long friendId) {
        friendshipService.unfriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getCommonFriends(@Valid @PathVariable long userId, @PathVariable long otherId) {
        return friendshipService.getCommonFriends(userId, otherId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriendsByUserId(@Valid @PathVariable long userId) {
        return friendshipService.getFriendsByUserId(userId);
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteFilm(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}