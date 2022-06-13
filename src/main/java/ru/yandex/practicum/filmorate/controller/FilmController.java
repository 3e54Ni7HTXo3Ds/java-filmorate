package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.Film.validateFilm;

@Slf4j

@RestController
public class FilmController {

    private long filmId;
    private final HashMap<Long, Film> films = new HashMap<>();

    private long getNextFilmId() {
        filmId++;
        return filmId;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Текущее количество фильмов: {} ", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        filmId = getNextFilmId();
        film.setId(filmId);
        films.put(filmId, film);
        log.info("Добавлен новый фильм: {} ", film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        long updateId = film.getId();

        if (films.containsKey(updateId)) {
            films.put(updateId, film);
            log.info("Обновлен фильм: {} ", film);
            return film;
        } else {
            filmId = getNextFilmId();
            film.setId(filmId);
            films.put(filmId, film);
            log.info("Ранее такого фильма не было. Добавлен новый фильм: {} ", film);
        }

        log.error("Ошибка обновления фильма: {} ", film);
        return null;
    }


}