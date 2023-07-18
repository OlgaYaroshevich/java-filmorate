package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MpaRatingDao {
    Optional<MpaRating> getMpaRatingById(long id);

    List<MpaRating> getMpaRatings();
}