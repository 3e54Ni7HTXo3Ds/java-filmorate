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
    private final TreeSet<Film> sortedFilms = new TreeSet<>(new LikesComparator());

    protected static class LikesComparator implements Comparator<Film> {
        @Override
        public int compare(Film o1, Film o2) {
            // сравниваем лайки фильмов — больше лайков в начало списка

         //   if ((o1.getLikes() != null && o2.getLikes() != null)) {
            return Integer.compare(o1.getLikes().size(), o2.getLikes().size());
            }
         //   return -1;
        }


    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Long id, Long userId) {
        sortedFilms.remove(filmStorage.findFilmById(id));
        filmStorage.findFilmById(id).getLikes().add(userId);
        sortedFilms.add(filmStorage.findFilmById(id));

    }

    public void deleteLike(Long id, Long userId) {
        sortedFilms.remove(filmStorage.findFilmById(id));
        filmStorage.findFilmById(id).getLikes().remove(userId);
        sortedFilms.add(filmStorage.findFilmById(id));
    }

    public List<Film> findPopular(Integer count) {
        return sortedFilms.stream().limit(count).collect(Collectors.toList());
    }

}
