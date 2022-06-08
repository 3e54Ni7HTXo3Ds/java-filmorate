package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;

@Slf4j
@Data
public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    public Film(long id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }


    public static void validateFilm(Film film) throws ValidationException {
        String message = null;
        if (film.getName().isBlank()) {
            message = "название не может быть пустым";
            log.error(message);
            throw new ValidationException(message);
        } else if (film.getDescription().length() >= 200) {
            message = "максимальная длина описания — 200 символов";
            log.error(message);
            throw new ValidationException(message);
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            message = "дата релиза — не раньше 28 декабря 1895 года";
            log.error(message);
            throw new ValidationException(message);
        } else if (film.getDuration() < 0) {
            message = "продолжительность фильма должна быть положительной";
            log.error(message);
            throw new ValidationException(message);
        } else if (film.getId() < 0) {
            message = "некорректный Id";
            log.error(message);
            throw new ValidationException(message);
        }
    }
}
