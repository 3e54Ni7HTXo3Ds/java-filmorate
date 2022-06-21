package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        userStorage.findUserById(userId).getFriends().add(friendId);
        userStorage.findUserById(friendId).getFriends().add(userId);
        log.info("Пользователь добавлен в друзья: {} ", userStorage.findUserById(friendId));
    }

    public void deleteFriend(Long userId, Long friendId) {
        userStorage.findUserById(userId).getFriends().remove(friendId);
        userStorage.findUserById(friendId).getFriends().remove(userId);
        log.info("Пользователь удален из друзей: {} ", userStorage.findUserById(friendId));
    }

    public ArrayList<User> findCommonFriends(Long userId, Long otherId) { //вывод списка общих друзей
        Set<Long> friends1 = userStorage.findUserById(userId).getFriends();
        Set<Long> friends2 = userStorage.findUserById(otherId).getFriends();

        ArrayList<User> friendsCommon = new ArrayList<>();
        for (Long id : friends1) {
            if (friends2.contains(id)) {
                friendsCommon.add(userStorage.findUserById(id));
            }
        }
        return friendsCommon;
    }

    public ArrayList<User> findFriends(Long userId) { //вывод списка друзей пользователя
        Set<Long> friends = userStorage.findUserById(userId).getFriends();
        ArrayList<User> friendsDetailed = new ArrayList<>();
        for (Long id : friends) {
            friendsDetailed.add(userStorage.findUserById(id));
        }
        return friendsDetailed;
    }
}
