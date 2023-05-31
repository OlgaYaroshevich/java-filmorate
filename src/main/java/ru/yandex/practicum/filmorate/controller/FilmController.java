package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping(value = "/films")
    public Film addNewFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.addNewFilm(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable("id") long filmId) {
        return filmService.getFilm(filmId);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") long idFilm,
                        @PathVariable("userId") long idUser) throws ValidationException {
        return filmService.addLike(idFilm, idUser);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") long idFilm,
                           @PathVariable("userId") long idUser) throws ValidationException {
        filmService.deleteLike(idFilm, idUser);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Optional<Integer> count) {
        if (count.isPresent()) {
            return filmService.getPopularFilms(count.get());
        }
        return filmService.getPopularFilms(10);
    }

    @DeleteMapping("/films/{id}/delete")
    public void deleteFilm(@PathVariable("id") long id) {
        filmService.deleteFilm(id);
    }
}
