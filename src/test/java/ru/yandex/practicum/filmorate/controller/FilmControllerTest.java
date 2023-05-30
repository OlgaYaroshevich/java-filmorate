package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    FilmController filmController;
    FilmService filmService;
    FilmStorage filmStorage;
    UserStorage userStorage;
    Film defaultFilm;
    Film beforeLocalDateFilm;
    Film unnamedFilm;
    Film overDescription;
    Film negativeDuration;
    Film updateFilm;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    void beforeEach() {
        filmStorage = new InMemoryFilmStorage();
        userStorage = new InMemoryUserStorage();
        filmService = new FilmService(filmStorage, userStorage);
        filmController = new FilmController(filmService);

        defaultFilm = new Film(
                1L,
                "Film",
                "Default Film",
                LocalDate.of(1989, 12, 20),
                Duration.ofMinutes(120)
        );

        beforeLocalDateFilm = new Film(
                2L,
                "Before Local Date Film",
                "before Local Date Film ",
                LocalDate.of(1895, 12, 24),
                Duration.ofMinutes(60)
        );

        unnamedFilm = new Film(
                3L,
                "",
                "Unnamed Film",
                LocalDate.of(1989, 12, 20),
                Duration.ofMinutes(120)
        );

        overDescription = new Film(
                4L,
                "Over Description Film",
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                        "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                LocalDate.of(1989, 12, 20),
                Duration.ofMinutes(120)
        );

        negativeDuration = new Film(
                5L,
                "Negative Duration Film",
                "Negative Duration Film",
                LocalDate.of(1989, 12, 20),
                Duration.ofMinutes(-120)
        );

        updateFilm = new Film(
                1L,
                "newFilm",
                "New interesting Film",
                LocalDate.of(1989, 10, 10),
                Duration.ofMinutes(30)
        );
    }

    @Test
    void addFilm() throws ValidationException {
        int size = filmController.getFilms().size();
        assertEquals(0, size, "Not null size");
        filmController.addNewFilm(defaultFilm);
        int size1 = filmController.getFilms().size();
        assertEquals(1, size1, "Отличается размер мапы");
        assertEquals(1, validator.validate(unnamedFilm).size());
        assertEquals(1, validator.validate(overDescription).size());
        assertEquals(1, validator.validate(negativeDuration).size());
        assertEquals(1, validator.validate(unnamedFilm).size());
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.addNewFilm(beforeLocalDateFilm));
        assertEquals("Дата релиза — не раньше 28 декабря 1895 года",
                exception.getMessage());
   }

    @Test
    void updateFilm() throws ValidationException {
        int size = filmController.getFilms().size();
        assertEquals(0, size, "Not null size");
        filmController.addNewFilm(defaultFilm);
        int size1 = filmController.getFilms().size();
        assertEquals(1, size1, "Отличается размер мапы");
        assertEquals(1, validator.validate(unnamedFilm).size());
        assertEquals(1, validator.validate(overDescription).size());
        assertEquals(1, validator.validate(negativeDuration).size());
        assertEquals(1, validator.validate(unnamedFilm).size());
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.addNewFilm(beforeLocalDateFilm));
        assertEquals("Дата релиза — не раньше 28 декабря 1895 года",
                exception.getMessage());
        filmController.updateFilm(updateFilm);
        Film example = filmController.getFilms().get(0);
        assertEquals(updateFilm, example, "Разные фильмы");
    }

    @Test
    void getAllFilms() throws ValidationException {
        int size = filmController.getFilms().size();
        assertEquals(0, size, "Not null size");
        filmController.addNewFilm(defaultFilm);
        int size1 = filmController.getFilms().size();
        assertEquals(1, size1, "Отличается размер мапы");
    }
}