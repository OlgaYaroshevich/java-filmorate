package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController;
    private User user;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    public void setUp() {
        user = User.builder().email("mail@email.ru").login("login")
                .birthday(LocalDate.parse("08-04-1989", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .name("Olga").build();
        userController = new UserController();
    }

    @Test
    public void userDefaultTest() {
        userController.addNewUser(user);
        assertEquals(1, userController.getUsers().size());
    }

    @Test
    public void idMissedTest() {
        assertThrows(ValidationException.class, () -> userController.updateUser(user));
    }

    @Test
    public void emailError() {
        user.setEmail("mail.ru");
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullEmailTest() {
        user.setEmail(null);
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void blankEmailTest() {
        user.setEmail(" ");
        assertEquals(3, validator.validate(user).size());
    }

    @Test
    public void nullLoginTest() {
        user.setLogin(null);
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void blankLoginTest() {
        user.setLogin("");
        assertEquals(2, validator.validate(user).size());
        user.setLogin(" ");
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullNameTest() {
        user.setName(null);
        assertEquals(user.getLogin(), "login");
    }

    @Test
    public void blankNameTest() {
        user.setName("");
        assertEquals(user.getLogin(), "login");
        user.setName(" ");
        assertEquals(user.getLogin(), "login");
    }

    @Test
    public void birthdayInFutureTest() {
        user.setBirthday(LocalDate.parse("08-04-2030", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(2, validator.validate(user).size());
    }

    @Test
    public void nullRequestTest() {
        User user = null;
        assertThrows(NullPointerException.class, () -> userController.addNewUser(user));
    }

    @Test
    public void negativeIdTest() {
        user.setId(-1);
        assertEquals(1, validator.validate(user).size());
    }
}