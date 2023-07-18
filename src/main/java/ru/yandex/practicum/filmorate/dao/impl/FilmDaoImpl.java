package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.constant.GenreConstant;
import ru.yandex.practicum.filmorate.constant.MpaRatingConstant;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.MpaRatingNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.constant.FilmConstant.*;

@Slf4j
@Component
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Film> addFilm(Film film) {
        log.debug("add({}).", film);
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(FILM_TABLE)
                .usingColumns(NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_RATING_ID)
                .usingGeneratedKeyColumns(ID)
                .executeAndReturnKeyHolder(Map.of(NAME, film.getName(),
                        DESCRIPTION, film.getDescription(),
                        RELEASE_DATE, java.sql.Date.valueOf(film.getReleaseDate()),
                        DURATION, film.getDuration(),
                        MPA_RATING_ID, film.getMpa().getId()))
                .getKeys();
        assert keys != null;
        film.setId((Long) keys.get(ID));
        return Optional.of(film);
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        String sql =
                "UPDATE FILMS SET FILM_ID = ?, NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, "
                        + "MPA_RATING_ID = ? WHERE FILM_ID = ? ";
        jdbcTemplate.update(sql,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return Optional.of(film);
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlToFilmTable = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sqlToFilmTable, (rs, rowNum) -> mapToFilm(rs))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        String sqlToFilmTable = "SELECT * FROM FILMS WHERE FILM_ID = ? ";
        return jdbcTemplate.query(sqlToFilmTable, (rs, rowNum) -> mapToFilm(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public void deleteFilm(long id) {
        String deleteSQL = "DELETE FROM FILMS WHERE FILM_ID = ?";
        this.jdbcTemplate.update(deleteSQL, id);
    }

    private Film mapToFilm(ResultSet filmRows) throws SQLException {
        return Film.builder()
                .id(filmRows.getLong(ID))
                .name(filmRows.getString(NAME))
                .description(filmRows.getString(DESCRIPTION))
                .releaseDate(filmRows.getDate(RELEASE_DATE).toLocalDate())
                .duration(filmRows.getInt(DURATION))
                .mpa(getMpaRatingById(filmRows.getLong(MPA_RATING_ID)).orElseThrow(MpaRatingNotFoundException::new))
                .likes(new ArrayList<>())
                .genres(getGenresByFilmId(filmRows.getLong(ID)))
                .build();
    }

    private Genre mapToGenre(ResultSet genreRows) throws SQLException {
        return Genre.builder()
                .id(genreRows.getLong(GenreConstant.ID))
                .name(genreRows.getString(GenreConstant.NAME))
                .build();
    }

    private List<Genre> getGenresByFilmId(long filmId) {
        String sqlToGenreTable = "SELECT gt.GENRE_ID, gt.NAME FROM GENRE AS gt " +
                "JOIN GENRE_ID AS gid ON gid.GENRE_ID = gt.GENRE_ID " +
                "WHERE gid.FILM_ID = ? ";
        return jdbcTemplate.query(sqlToGenreTable, (rs, rowNum) -> mapToGenre(rs), filmId)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private MpaRating mapToMpaRating(ResultSet mpaRatingRows) throws SQLException {
        return new MpaRating(
                mpaRatingRows.getLong(MpaRatingConstant.ID),
                mpaRatingRows.getString(MpaRatingConstant.NAME));
    }

    private Optional<MpaRating> getMpaRatingById(long id) {
        String sqlToMpaRatingTable = "SELECT * FROM MPA_RATING WHERE RATING_ID = ? ";
        return jdbcTemplate.query(sqlToMpaRatingTable, (rs, rowNum) -> mapToMpaRating(rs), id)
                .stream()
                .filter(Objects::nonNull)
                .findFirst();
    }

}