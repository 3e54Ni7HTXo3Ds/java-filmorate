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

    public TreeSet<Film> getSortedFilms() {
        return sortedFilms;
    }

    private final TreeSet<Film> sortedFilms = new TreeSet<>(new LikesComparator());

    protected static class LikesComparator implements Comparator<Film> {
        @Override
        public int compare(Film o1, Film o2) {
            // сравниваем лайки фильмов — больше лайков в начало списка
            if (o1.getLikes().size()>o2.getLikes().size()) {
                return 1;
            } else if (o1.getLikes().size()<o2.getLikes().size()) {
                return -1;
            } else if (o1.getLikes().size()==o2.getLikes().size() && !o1.equals(o2)){
                return 1;
            } else return 0;

        }
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
            sortedFilms.add(film);
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
                sortedFilms.remove(findFilmById(film.getId()));
                films.put(updateId, film);
                sortedFilms.add(film);

                log.info("Обновлен фильм: {} ", film);
                return film;
            } else {
                filmId = getNextFilmId();
                film.setId(filmId);
                films.put(filmId, film);
                sortedFilms.add(film);
                log.info("Ранее такого фильма не было. Добавлен новый фильм: {} ", film);
            }
        } else
            log.error("Ошибка обновления фильма: {} ", film);
        return null;
    }


    @Override
    public void deleteFilm(Film film) {
        if (film != null) {
            sortedFilms.remove(film);
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
