package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundDataException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.model.Film.validateFilm;

@Slf4j

@RestController
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film findFilmById(@PathVariable Long id) throws NotFoundDataException {
        if (id < 0) {
            throw new NotFoundDataException();
        } else
            return filmService.findFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> findPopular(@RequestParam(defaultValue = "10") Integer count) throws NotFoundDataException, ValidationException {
        if (count > 0) {
            return filmService.findPopular(count);
        } else throw new ValidationException("Значение count должно быть положительным");
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException, NotFoundDataException {
        validateFilm(film);
        return filmService.createFilm(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException, NotFoundDataException {
        validateFilm(film);
        return filmService.updateFilm(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) throws NotFoundDataException {
        if (id < 0 || userId < 0) {
            throw new NotFoundDataException();
        } else {
            filmService.addLike(id, userId);
        }
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) throws NotFoundDataException {
        if (id < 0 || userId < 0) {
            throw new NotFoundDataException();
        } else {
            filmService.deleteLike(id, userId);
        }
    }
}