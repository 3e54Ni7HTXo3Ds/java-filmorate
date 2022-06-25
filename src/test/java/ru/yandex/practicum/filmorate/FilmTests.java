package ru.yandex.practicum.filmorate;

import com.google.gson.*;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilmTests {
    private final HttpClient client = HttpClient.newHttpClient();
    public Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    static class LocalDateAdapter implements JsonSerializer<LocalDate> {

        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
        }
    }


    @Test
    void createFilm() throws IOException, InterruptedException {
        LocalDate Date1 = LocalDate.of(2022, 5, 3);
        Film newFilm1 = new Film(1, "1", "Film", Date1, 60);
        URI url1 = URI.create("http://localhost:8080/films/");
        String json1 = gson.toJson(newFilm1);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(200, response.statusCode());
        assertEquals("{\"id\":1,\"name\":\"1\",\"description\":\"Film\",\"releaseDate\":\"2022-05-03\"," +
                        "\"duration\":60,\"likes\":[]}",
                response.body());
    }

    @Test
    void validateFilmName() throws IOException, InterruptedException {
        LocalDate Date1 = LocalDate.of(2022, 6, 3);
        Film newFilm1 = new Film(1, "", "Film", Date1, 60);
        URI url1 = URI.create("http://localhost:8080/films/");
        String json1 = gson.toJson(newFilm1);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(400, response.statusCode());

    }

    @Test
    void validateFilmDescription() throws IOException, InterruptedException {
        LocalDate Date1 = LocalDate.of(2022, 6, 3);
        String desc = "Любовь? Что такое любовь? Любовь мешает смерти. Любовь есть жизнь. Все, все что я понимаю," +
                " я понимаю только потому, что люблю. Все есть, все существует только потому, что я люблю." +
                " Все связано одною ею. Любовь есть Бог, и умереть — значит мне, частице любви, вернуться к общему" +
                " и вечному источнику.";
        Film newFilm1 = new Film(1, "1", desc, Date1, 60);
        URI url1 = URI.create("http://localhost:8080/films/");
        String json1 = gson.toJson(newFilm1);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(400, response.statusCode());

    }
    @Test
    void validateFilmReleaseDate() throws IOException, InterruptedException {
        LocalDate Date1 = LocalDate.of(1895, 12, 27);
        String desc = "";
        Film newFilm1 = new Film(1, "1", desc, Date1, 60);
        URI url1 = URI.create("http://localhost:8080/films/");
        String json1 = gson.toJson(newFilm1);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(400, response.statusCode());

    }

    @Test
    void validateFilmDuration() throws IOException, InterruptedException {
        LocalDate Date1 = LocalDate.of(1895, 12, 29);
        String desc = "2";
        Film newFilm1 = new Film(1, "1", desc, Date1, -1);
        URI url1 = URI.create("http://localhost:8080/films/");
        String json1 = gson.toJson(newFilm1);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(400, response.statusCode());

    }

    @Test
    void validateFilmId() throws IOException, InterruptedException {
        LocalDate Date1 = LocalDate.of(1895, 12, 29);
        String desc = "2";
        Film newFilm1 = new Film(-1, "1", desc, Date1, 3);
        URI url1 = URI.create("http://localhost:8080/films/");
        String json1 = gson.toJson(newFilm1);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(404, response.statusCode());

    }
}
