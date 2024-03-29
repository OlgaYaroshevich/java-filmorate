package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dbStorage.friendship.FriendshipStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipStorage friendshipStorage;

    private final UserService userService;

    public List<Long> getFriendIdsByUserId(long userId) {
        List<Long> friends = friendshipStorage.getFriendIdsByUserId(userId);

        return userService.getUsersByIds(friends)
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public Friendship addFriend(long userId, long friendId) {
        userService.getUserById(userId);
        userService.getUserById(friendId);
        return friendshipStorage.createFriendship(Friendship.builder()
                .userId(userId)
                .friendId(friendId)
                .build());
    }

    public void unfriend(long userId, long friendId) {
        friendshipStorage.deleteFriendship(Friendship.builder()
                .userId(userId)
                .friendId(friendId)
                .build());
    }

    public List<User> getCommonFriends(long userId1, long userId2) {
        Set<Long> userFriendsId = new HashSet<>(friendshipStorage.getFriendIdsByUserId(userId1));
        Set<Long> friendFriendsId = new HashSet<>(friendshipStorage.getFriendIdsByUserId(userId2));
        List<Long> commonFriendsIds = new ArrayList<>(getCommonElements(userFriendsId, friendFriendsId));
        return userService.getUsersByIds(commonFriendsIds);
    }

    public List<User> getFriendsByUserId(long userId) {
        List<Long> friends = friendshipStorage.getFriendIdsByUserId(userId);

        return userService.getUsersByIds(friends);
    }

    private static <T> Set<T> getCommonElements(Set<T> first, Set<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }
}