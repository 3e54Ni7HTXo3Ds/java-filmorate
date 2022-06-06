package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.User.validateUser;


@Slf4j
@RestController
public class UserController {
    private long userId;
    private final List<User> users = new ArrayList<>();

    private long getNextUserId() {
        userId++;
        return userId;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Текущее количество пользователей: {} ", users.size());
        return users;
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        userId = getNextUserId();
        user.setId(userId);
        users.add(user);
        log.info("Добавлен новый пользователь: {} ", user);
        return user;
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        long updateId = user.getId();
        User oldUser = null;
        boolean isPresent = false;
        for (User item : users)
            if (item.getId() == updateId) {
                isPresent = true;
                oldUser = item;
                break;
            }
        if (isPresent) {
            users.remove(oldUser);
            users.add(user);
            log.info("Обновлен пользователь: {} ", user);
            return user;
        } else {
            userId = getNextUserId();
            user.setId(userId);
            users.add(user);
            log.info("Ранее такого пользователя не было. Добавлен новый пользователь: {} ", user);
        }
        log.error("Ошибка обновления пользователя: {} ", user);
        return null;
    }
}