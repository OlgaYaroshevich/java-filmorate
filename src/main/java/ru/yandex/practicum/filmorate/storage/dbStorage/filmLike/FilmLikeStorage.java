package ru.yandex.practicum.filmorate.storage.dbStorage.filmLike;

import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.List;

public interface FilmLikeStorage {
    FilmLike createLike(FilmLike filmLike);

    List<FilmLike> getFilmLikes(long filmId);

    void unlike(FilmLike filmLike);
}