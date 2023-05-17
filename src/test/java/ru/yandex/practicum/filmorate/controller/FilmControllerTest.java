package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;
    private Film film;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    public void setUp() {
        film = Film.builder()
                .name("Film_1")
                .description("Description_Film_1")
                .releaseDate(LocalDate.parse("08-04-1989", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .duration(Duration.ofMinutes(120))
                .build();
        filmController = new FilmController();
    }

    @Test
    public void filmDefaultTest() {
        filmController.addNewFilm(film);
        assertEquals(1, filmController.getFilms().size());
    }

    @Test
    public void blankNameTest() {
        film.setName(null);
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void nullNameTest() {
        film.setName("");
        assertEquals(1, validator.validate(film).size());
        film.setName(" ");
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void moreThanTwoHundredCharactersDescriptionTest() {
        film.setDescription("DescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription");
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void releaseDateBefore28121895Test() {
        film.setReleaseDate(LocalDate.parse("28-12-1894", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));
    }

    @Test
    public void releaseDateInFutureTest() {
        film.setReleaseDate(LocalDate.parse("28-12-2030", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void negativeDurationTest() {
        film.setDuration(Duration.ofSeconds(-120));
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    public void nullRequestTest() {
        Film film = null;
        assertThrows(NullPointerException.class, () -> filmController.addNewFilm(film));
    }

    @Test
    public void idMissedTest() {
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));
    }

    @Test
    public void negativeIdTest() {
        film.setId(-1);
        assertEquals(1, validator.validate(film).size());
    }}