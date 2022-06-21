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


    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film findFilmById(@PathVariable Long id) throws NotFoundDataException {
        if (id < 0) {
            throw new NotFoundDataException();
        } else
            return filmStorage.findFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> findPopular(@RequestParam(defaultValue = "10") Integer count) throws NotFoundDataException {
        return filmService.findPopular(count);
    }


    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException, NotFoundDataException {
        validateFilm(film);
        return filmStorage.createFilm(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException, NotFoundDataException {
        validateFilm(film);
        return filmStorage.updateFilm(film);
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