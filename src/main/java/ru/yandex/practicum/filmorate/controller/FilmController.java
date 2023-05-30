package ru.yandex.practicum.filmorate.controller;

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
public class FilmController {

    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping(value = "/films")
    @ResponseBody
    public Film addNewFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.addNewFilm(film);
    }

    @PutMapping(value = "/films")
    @ResponseBody
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
    public void delLike(@PathVariable("id") long idFilm,
                        @PathVariable("userId") long idUser) throws ValidationException {
        filmService.delLike(idFilm, idUser);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getPopularFilms(@RequestParam(required = false) Optional<Integer> count) {
        if (count.isPresent()) {
            return filmService.getPopularFilms(count.get());
        }
        return filmService.getPopularFilms(10);
    }

    @DeleteMapping("/films/{id}/delete")
    public void delFilm(@PathVariable("id") long id) {
        filmService.delFilm(id);
    }
}
