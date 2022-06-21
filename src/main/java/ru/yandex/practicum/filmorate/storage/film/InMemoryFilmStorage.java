package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@Slf4j
@Component
@Data
public class InMemoryFilmStorage implements FilmStorage {

    private long filmId;
    private final HashMap<Long, Film> films = new HashMap<>();
    private FilmService filmService;

    @Autowired
    public InMemoryFilmStorage(FilmService filmService) {
        this.filmService = filmService;
    }

    private long getNextFilmId() {
        filmId++;
        return filmId;
    }

    @Override
    public Film createFilm(Film film) {
        if (film != null) {
            filmId = getNextFilmId();
            film.setId(filmId);
            films.put(filmId, film);
            filmService.getSortedFilms().add(film);
            log.info("Добавлен новый фильм: {} ", film);
            return film;
        } else {
            log.error("Ошибка создания фильма: {} ", film);
            return null;
        }
    }

    @Override
    public Film updateFilm(Film film) {
        if (film != null) {
            long updateId = film.getId();
            if (films.containsKey(updateId)) {
                filmService.getSortedFilms().remove(findFilmById(film.getId()));
                films.put(updateId, film);
                filmService.getSortedFilms().add(film);

                log.info("Обновлен фильм: {} ", film);
                return film;
            } else {
                filmId = getNextFilmId();
                film.setId(filmId);
                films.put(filmId, film);
                filmService.getSortedFilms().add(film);
                log.info("Ранее такого фильма не было. Добавлен новый фильм: {} ", film);
            }
        } else
            log.error("Ошибка обновления фильма: {} ", film);
        return null;
    }


    @Override
    public void deleteFilm(Film film) {
        if (film != null) {
            filmService.getSortedFilms().remove(film);
            films.remove(film.getId());
            log.info("Удален фильм: {} ", film);
        }
    }

    @Override

    public List<Film> findAllFilms() {
        log.info("Текущее количество фильмов: {} ", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findFilmById(Long id) {
        return films.get(id);
    }

}
