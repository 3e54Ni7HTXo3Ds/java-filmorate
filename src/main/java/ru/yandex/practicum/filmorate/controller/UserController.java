package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundDataException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.User.validateUser;


@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Long id) throws NotFoundDataException {
        if (id < 0) {
            throw new NotFoundDataException();
        } else
            return userService.findUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public ArrayList<User> findFriends(@PathVariable Long id) {
        return userService.findFriends(id);
    }


    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ArrayList<User> findCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.findCommonFriends(id, otherId);
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) throws ValidationException, NotFoundDataException {
        validateUser(user);
        return userService.createUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException, NotFoundDataException {
        validateUser(user);
        return userService.updateUser(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) throws NotFoundDataException {
        if (id < 0 || friendId < 0) {
            throw new NotFoundDataException();
        } else {
            userService.addFriend(id, friendId);
        }
    }

    @DeleteMapping(value = "/users")
    public void deleteUser(@Valid @RequestBody User user) throws ValidationException, NotFoundDataException {
        validateUser(user);
        userService.deleteUser(user);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) throws ValidationException {
        userService.deleteFriend(id, friendId);
    }
}