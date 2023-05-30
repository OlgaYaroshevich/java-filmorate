package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addNewFilm(Film film) throws ValidationException;

    Film updateFilm(Film film) throws ValidationException;


    Film getFilm(long idFilm);


    List<Film> getFilms();


    void delFilm(long id);
}
