package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dbStorage.genre.GenreStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Genre getGenreById(long genreId) {
        var genre = genreStorage.getGenreById(genreId);
        if (genre.isPresent()) {
            return genre.get();
        }
        throw new GenreNotFoundException(
                String.format("Жанр с таким id %s не существует", genreId));

    }

    public List<Genre> getGenres() {
        List<Genre> genres = genreStorage.getGenres();
        log.info("Получены жанры: " + genres);
        return genres;
    }
}