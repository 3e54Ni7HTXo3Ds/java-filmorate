package ru.yandex.practicum.filmorate.storage.user;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class InMemoryUserStorage implements UserStorage {

    @Override
    public User createUser() {
        return null;
    }

    @Override
    public User updateUser() {
        return null;
    }

    @Override
    public User deleteUser() {
        return null;
    }
}
