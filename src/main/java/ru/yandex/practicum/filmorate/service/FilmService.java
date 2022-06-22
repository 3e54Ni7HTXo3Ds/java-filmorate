package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Long id, Long userId) {
        filmStorage.getSortedFilms().remove(filmStorage.findFilmById(id));
        filmStorage.findFilmById(id).getLikes().add(userId);
        filmStorage.getSortedFilms().add(filmStorage.findFilmById(id));
    }

    public void deleteLike(Long id, Long userId) {
        filmStorage.getSortedFilms().remove(filmStorage.findFilmById(id));
        filmStorage.findFilmById(id).getLikes().remove(userId);
        filmStorage.getSortedFilms().add(filmStorage.findFilmById(id));
    }

    public List<Film> findPopular(Integer count) {
        return filmStorage.getSortedFilms().stream().limit(count).collect(Collectors.toList());
    }

}
