package ru.yandex.practicum.filmorate.storage.user;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
@Data
public class InMemoryUserStorage implements UserStorage {

    private long userId;
    private final HashMap<Long, User> users = new HashMap<>();

    private long getNextUserId() {
        userId++;
        return userId;
    }

    @Override
    public User createUser(User user) {
        if (user != null) {
            userId = getNextUserId();
            user.setId(userId);
            users.put(userId, user);
            log.info("Добавлен новый пользователь: {} ", user);
            return user;
        } else {
            log.error("Ошибка создания пользователя: {} ", user);
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        if (user != null) {
            long updateId = user.getId();
            if (users.containsKey(updateId)) {
                users.put(updateId, user);
                log.info("Обновлен пользователь: {} ", user);
                return user;
            } else {
                userId = getNextUserId();
                user.setId(userId);
                users.put(userId, user);
                log.info("Ранее такого пользователя не было. Добавлен новый пользователь: {} ", user);
            }
        } else
            log.error("Ошибка обновления пользователя: {} ", user);
        return null;
    }

    @Override
    public void deleteUser(User user) {
        if (user != null && users.containsKey(user.getId())) {
            users.remove(user.getId());
            log.info("Удален пользователь: {} ", user);
        } else {
            throw new ValidationException("Сработала валидация: Такого пользователя не существует");
        }
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Текущее количество пользователей: {} ", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(Long id) {
        return users.getOrDefault(id, null);
    }
}
