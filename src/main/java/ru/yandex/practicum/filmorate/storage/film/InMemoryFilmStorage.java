package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
                films.put(updateId, film);
                log.info("Обновлен фильм: {} ", film);
                return film;
            } else {
                filmId = getNextFilmId();
                film.setId(filmId);
                films.put(filmId, film);
                log.info("Ранее такого фильма не было. Добавлен новый фильм: {} ", film);
            }
        } else
            log.error("Ошибка обновления фильма: {} ", film);
        return null;
    }


    @Override
    public void deleteFilm(Film film) {
        if (film != null) {
            films.remove(film.getId());
            log.info("Удален пользователь: {} ", film);
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
