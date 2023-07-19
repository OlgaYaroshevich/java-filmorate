package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipDao {
    Friendship createFriendship(Friendship friendship);

    void deleteFriendship(Friendship friendship);

    List<Long> getFriendIdsByUserId(long userId);

    Optional<Friendship> getFriendship(Friendship friendship);
}
