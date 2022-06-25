package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundDataException extends Throwable{
    public NotFoundDataException() {
    }

    public NotFoundDataException(String message) {
        super(message);
    }

    public NotFoundDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
