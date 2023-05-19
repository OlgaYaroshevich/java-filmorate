package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private int nextId = 1;

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        checkReleaseDate(film);
        film.setId(nextId++);
        filmsMap.put(film.getId(), film);
        log.debug("Добавлен новый фильм: " + film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        checkReleaseDate(film);
        if (!filmsMap.containsKey(film.getId())) {
            log.error("Фильм с id {} не найден", film.getId());
            throw new ValidationException();
        }
        filmsMap.put(film.getId(), film);
        log.debug("Фильм обновлен: " + film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Получение списка фильмов");
        return new ArrayList<>(filmsMap.values());
    }

    private void checkReleaseDate(Film film) {
        if (film.getReleaseDate()
                .isBefore(LocalDate.parse("28-12-1895", DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
            log.error("Ошибка валидации");
            throw new ValidationException();
        }
    }
}
