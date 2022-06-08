package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.Film.validateFilm;

@Slf4j

@RestController
public class FilmController {

    private long filmId;
    private final List<Film> films = new ArrayList<>();


    private long getNextFilmId() {
        filmId++;
        return filmId;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Текущее количество фильмов: {} ", films.size());
        return films;
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        filmId = getNextFilmId();
        film.setId(filmId);
        films.add(film);
        log.info("Добавлен новый фильм: {} ", film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        long updateId = film.getId();
        Film oldFilm = null;
        boolean isPresent = false;

        for (Film item : films)
            if (item.getId() == updateId) {
                isPresent = true;
                oldFilm = item;
                break;
            }
        if (isPresent) {
            films.remove(oldFilm);
            films.add(film);
            log.info("Обновлен фильм: {} ", film);
            return film;
        } else {
            filmId = getNextFilmId();
            film.setId(filmId);
            films.add(film);
            log.info("Ранее такого фильма не было. Добавлен новый фильм: {} ", film);
        }

        log.error("Ошибка обновления фильма: {} ", film);
        return null;
    }


}