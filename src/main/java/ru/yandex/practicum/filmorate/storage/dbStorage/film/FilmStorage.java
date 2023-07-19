package ru.yandex.practicum.filmorate.storage.dbStorage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getAllFilms();

    Optional<Film> updateFilm(Film film);

    Optional<Film> createFilm(Film film);

    Optional<Film> getFilmById(long id);

    void deleteFilm(long id);
}