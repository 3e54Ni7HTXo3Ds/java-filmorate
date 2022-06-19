package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
       return filmStorage.createFilm(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        return filmStorage.updateFilm(film);
    }


}