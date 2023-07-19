package ru.yandex.practicum.filmorate.storage.dbStorage.friendship;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

public interface FriendshipStorage {
    Friendship createFriendship(Friendship friendship);

    void deleteFriendship(Friendship friendship);

    List<Long> getFriendIdsByUserId(long userId);
}