package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.NotFoundDataException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
public class User {
    private long id;
    @Email
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends;

    public User(long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public static void validateUser(User user) throws ValidationException, NotFoundDataException {
        String message = null;
        if (user.getEmail().isBlank() || !user.getEmail().contains("@") || user.getEmail() == null) {
            message = "электронная почта не может быть пустой и должна содержать символ @";
            log.error(message);
            throw new ValidationException(message);
        } else if (user.getLogin().isBlank() || user.getLogin() == null) {
            message = "логин не может быть пустым и содержать пробелы";
            log.error(message);
            throw new ValidationException(message);
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            message = "дата рождения не может быть в будущем";
            log.error(message);
            throw new ValidationException(message);
        } else if (user.getId() < 0) {
            message = "некорректный Id";
            log.error(message);
            throw new NotFoundDataException(message);
        } else if (user.getName().isBlank()) {
            message = "имя для отображения может быть пустым — в таком случае будет использован логин";
            log.info(message);
            user.setName(user.getLogin());
        }
    }
}
