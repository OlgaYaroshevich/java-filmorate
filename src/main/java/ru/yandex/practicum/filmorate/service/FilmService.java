package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dbStorage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    private final FilmGenreDao filmGenreDao;

    public Film getFilmById(long filmId) {
        Film film = filmStorage.getFilmById(filmId).orElseThrow(FilmNotFoundException::new);
        log.info("Получен фильм id = " + filmId);
        return film;
    }

    public List<Film> getFilms() {
        List<Film> films = filmStorage.getAllFilms();
        if (CollectionUtils.isEmpty(films)) {
            log.info("Получены фильмы: " + films);
            return films;
        }
        log.info("Получен пустой список фильмов");
        return new ArrayList<>(films);
    }

    public Film updateFilm(Film film) {
        if (!isExist(film.getId())) {
            log.error("Ошибка, фильм не найден: " + film);
            throw new FilmNotFoundException();
        }
        return filmStorage.updateFilm(film).get();
    }

    public Film createFilm(Film film) {
        filmStorage.createFilm(film);
        if (!CollectionUtils.isEmpty(film.getGenres())) {
            for (Genre genre : film.getGenres()) {
                filmGenreDao.linkGenreToFilm(film.getId(), genre.getId());
            }
        }

        log.info("Фильм {} создан.", film.getName());
        return film;
    }

    private boolean isExist(long id) {
        return filmStorage.getFilmById(id).isPresent();
    }

    public void deleteFilm(long id) {
        filmStorage.deleteFilm(id);
    }

}