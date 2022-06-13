package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    Film createFilm();

    Film updateFilm();

    Film delete();

}
