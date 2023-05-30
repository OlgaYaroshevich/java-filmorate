package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addNewFilm(Film film) throws ValidationException {
        return filmStorage.addNewFilm(film);
    }

    public Film updateFilm(Film film) throws ValidationException {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(long idFilm) {
        return filmStorage.getFilm(idFilm);
    }

    public Film addLike(long idFilm, long idUser) throws ValidationException {
        User user = userStorage.getUser(idUser);
        Film film = filmStorage.getFilm(idFilm);
        film.setPopularFilms(user.getId());
        return film;
    }

    public void delLike(long idFilm, long idUser) throws ValidationException {
        User user = userStorage.getUser(idUser);
        Film film = filmStorage.getFilm(idFilm);
        film.getPopularFilms().remove(user.getId());
    }

    public Collection<Film> getPopularFilms(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(f -> f.getPopularFilms().size());
        return filmStorage
                .getFilms()
                .stream()
                .sorted(comparator.reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }

    public void delFilm(long id) {
        filmStorage.delFilm(id);
    }
}
