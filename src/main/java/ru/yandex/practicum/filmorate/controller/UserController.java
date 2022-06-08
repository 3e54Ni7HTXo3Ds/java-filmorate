package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.User.validateUser;


@Slf4j
@RestController
public class UserController {
    private long userId;
    private final HashMap<Long, User> users = new HashMap<>();

    private long getNextUserId() {
        userId++;
        return userId;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Текущее количество пользователей: {} ", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        userId = getNextUserId();
        user.setId(userId);
        users.put(userId, user);
        log.info("Добавлен новый пользователь: {} ", user);
        return user;
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        long updateId = user.getId();
        if (users.containsKey(updateId)) {
            users.remove(updateId);
            users.put(updateId, user);
            log.info("Обновлен пользователь: {} ", user);
            return user;
        } else {
            userId = getNextUserId();
            user.setId(userId);
            users.put(userId, user);
            log.info("Ранее такого пользователя не было. Добавлен новый пользователь: {} ", user);
        }
        log.error("Ошибка обновления пользователя: {} ", user);
        return null;
    }
}