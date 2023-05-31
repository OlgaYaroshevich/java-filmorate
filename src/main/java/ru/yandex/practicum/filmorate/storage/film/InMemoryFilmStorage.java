package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> filmsMap = new HashMap<>();
    long id = 0L;

    private Long countId() {
        return ++id;
    }

    @Override
    public Film addNewFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 25))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        film.setId(countId());
        filmsMap.put(film.getId(), film);
        log.debug("Фильм добавлен {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 25))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (filmsMap.containsKey(film.getId())) {
            filmsMap.put(film.getId(), film);
            log.debug("Фильм обновлен {}", film);
            return film;
       } else {
            throw new FilmNotFoundException("Такого id не существует");
        }
    }

    @Override
    public Film getFilm(long idFilm) {
        if (filmsMap.containsKey(idFilm)) {
            return filmsMap.get(idFilm);
        }
        throw new FilmNotFoundException("Такого id не существует");
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmsMap.values());
    }

    public void deleteFilm(long id) {
        log.debug("Удаление фильма {}", id);
        filmsMap.remove(id);
    }
}
