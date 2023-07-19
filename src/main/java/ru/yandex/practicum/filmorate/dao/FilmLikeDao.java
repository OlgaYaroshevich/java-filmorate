package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.List;

public interface FilmLikeDao {
    long createLike(FilmLike filmLike);

    List<FilmLike> getFilmLikes(long filmId);

    void unlike(FilmLike filmLike);
}
