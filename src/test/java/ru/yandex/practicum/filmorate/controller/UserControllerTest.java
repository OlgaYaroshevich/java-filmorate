package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController userController;
    FilmStorage filmStorage;
    UserStorage userStorage;
    UserService userService;
    User defaultUser;
    User noEmailUser1;
    User noEmailUser2;
    User noLoginUser;
    User noNameUser;
    User wrongBirthdayUser;
    User updateUser;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    void beforeEach() {
        filmStorage = new InMemoryFilmStorage();
        userStorage = new InMemoryUserStorage();
        userService = new UserService(filmStorage, userStorage);
        userController = new UserController(userService);
        defaultUser = new User(
                1L,
                "email@mail.ru",
                "Login",
                "Name",
                LocalDate.of(1989, 4, 8)
        );

        noEmailUser1 = new User(
                2L,
                "",
                "Login",
                "Name",
                LocalDate.of(1989, 4, 8)
        );

        noEmailUser2 = new User(
                3L,
                "emailmailru",
                "Login",
                "Name",
                LocalDate.of(1989, 4, 8)
        );

        noLoginUser = new User(
                4L,
                "email@mail.ru",
                "",
                "Name",
                LocalDate.of(1989, 4, 8)
        );

        noNameUser = new User(
                5L,
                "email@mail.com",
                "Login",
                "",
                LocalDate.of(1989, 4, 8)
        );

        wrongBirthdayUser = new User(
                6L,
                "email@mail.ru",
                "Login",
                "Name",
                LocalDate.of(2089, 4, 8)
        );

        updateUser = new User(
                1L,
                "email@mail.ru",
                "newLogin",
                "newName",
                LocalDate.of(1999, 6, 10)
        );
    }

    @Test
    void getAllUsers() throws ValidationException {
        int size = userController.getUsers().size();
        assertEquals(0, size, "Not null size");
        userController.addNewUser(defaultUser);
        int size1 = userController.getUsers().size();
        assertEquals(1, size1, "Отличается размер мапы");
    }

    @Test
    void create() throws ValidationException {
        int size = userController.getUsers().size();
        assertEquals(0, size, "Not null size");
        userController.addNewUser(defaultUser);
        int size1 = userController.getUsers().size();
        assertEquals(1, size1, "Отличается размер мапы");
        assertEquals(1, validator.validate(noEmailUser1).size());
        assertEquals(1, validator.validate(noEmailUser2).size());
        assertEquals(1, validator.validate(noLoginUser).size());
        assertEquals(1, validator.validate(wrongBirthdayUser).size());
        userController.addNewUser(noNameUser);
        String login = noNameUser.getLogin();
        String name = noNameUser.getName();
        assertEquals(login, name);
    }

    @Test
    void update() throws ValidationException {
        int size = userController.getUsers().size();
        assertEquals(0, size, "Not null size");
        userController.addNewUser(defaultUser);
        int size1 = userController.getUsers().size();
        assertEquals(1, size1, "Отличается размер мапы");
        assertEquals(1, validator.validate(noEmailUser1).size());
        assertEquals(1, validator.validate(noEmailUser2).size());
        assertEquals(1, validator.validate(noLoginUser).size());
        assertEquals(1, validator.validate(wrongBirthdayUser).size());
        userController.addNewUser(noNameUser);
        String login = noNameUser.getLogin();
        String name = noNameUser.getName();
        assertEquals(login, name);
        userController.updateUser(updateUser);
        User example = userController.getUsers().get(0);
        assertEquals(updateUser, example);
    }
}