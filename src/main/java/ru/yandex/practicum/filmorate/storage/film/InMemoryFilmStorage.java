package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    @Override
    public Film createFilm() {
        return null;
    }

    @Override
    public Film updateFilm() {
        return null;
    }

    @Override
    public Film delete() {
        return null;
    }
}
