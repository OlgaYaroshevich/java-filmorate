package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> getGenres();

    List<Genre> getGenresByFilmId(long id);

    Optional<Genre> getGenreById(long id);
}
