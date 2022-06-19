package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final TreeSet<Film> sortedFilms = new TreeSet<>(new LikesComparator());

    protected static class LikesComparator implements Comparator<Film> {
        @Override
        public int compare(Film o1, Film o2) {
            // сравниваем лайки фильмов — больше лайков в начало списка

            if ((o1.getLikes() != null && o2.getLikes() != null)) {
                if (o1.getLikes().size() > o2.getLikes().size()) {
                    return 1;
                } else if (o1.getLikes().size() < o2.getLikes().size()) {
                    return -1;
                } else {
                    return 0;
                }
            }
            return -1;
        }
    }

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    void addLike(Long id, Long userId) {
        sortedFilms.remove(filmStorage.findFilmById(id));
        filmStorage.findFilmById(id).getLikes().add(userId);
        sortedFilms.add(filmStorage.findFilmById(id));

    }

    void deleteLike(Long id, Long userId) {
        sortedFilms.remove(filmStorage.findFilmById(id));
        filmStorage.findFilmById(id).getLikes().remove(userId);
        sortedFilms.add(filmStorage.findFilmById(id));
    }

    public ArrayList<Film> findPopular() {
        return new ArrayList<>(sortedFilms);
    }

}
