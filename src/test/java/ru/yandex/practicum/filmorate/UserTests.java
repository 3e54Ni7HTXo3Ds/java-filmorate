package ru.yandex.practicum.filmorate;

import com.google.gson.*;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

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

public class UserTests {
    private final HttpClient client = HttpClient.newHttpClient();
    public Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new UserTests.LocalDateAdapter())
            .create();

    static class LocalDateAdapter implements JsonSerializer<LocalDate> {

        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
        }
    }

    @Test
    void createUser() throws IOException, InterruptedException {
        LocalDate birthday = LocalDate.of(2022, 5, 3);
        User newUser = new User(1, "1@1.com", "user", "Pasha", birthday);
        URI url1 = URI.create("http://localhost:8080/users/");
        String json1 = gson.toJson(newUser);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(200, response.statusCode());
        assertEquals("{\"id\":1,\"email\":\"1@1.com\",\"login\":\"user\",\"name\":\"Pasha\",\"birthday\":" +
                        "\"2022-05-03\"}",
                response.body());
    }

    @Test
    void validateUserEmail() throws IOException, InterruptedException {
        LocalDate birthday = LocalDate.of(2022, 5, 3);
        User newUser = new User(1, "", "user", "Pasha", birthday);
        URI url1 = URI.create("http://localhost:8080/users/");
        String json1 = gson.toJson(newUser);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(500, response.statusCode());

    }

    @Test
    void validateUserLogin() throws IOException, InterruptedException {
        LocalDate birthday = LocalDate.of(2022, 5, 3);
        User newUser = new User(1, "1@1.com", "", "Pasha", birthday);
        URI url1 = URI.create("http://localhost:8080/users/");
        String json1 = gson.toJson(newUser);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(500, response.statusCode());

    }

    @Test
    void validateUserBirthday() throws IOException, InterruptedException {
        LocalDate birthday = LocalDate.now().plusDays(1);
        User newUser = new User(1, "1@1.com", "user", "Pasha", birthday);
        URI url1 = URI.create("http://localhost:8080/users/");
        String json1 = gson.toJson(newUser);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(500, response.statusCode());

    }

    @Test
    void validateUserId() throws IOException, InterruptedException {
        LocalDate birthday = LocalDate.now().plusDays(0);
        User newUser = new User(-1, "1@1.com", "user", "Pasha", birthday);
        URI url1 = URI.create("http://localhost:8080/users/");
        String json1 = gson.toJson(newUser);

        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response, "Нет ответа");
        assertEquals(500, response.statusCode());

    }

    @Test
    void validateEmptyUser() throws IOException, InterruptedException {
        User newUser = null;
        URI url1 = URI.create("http://localhost:8080/users/");
        String json1 = gson.toJson(newUser);
        String json2 = "{}";
        HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body1).build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(json2);
        HttpRequest request2 = HttpRequest.newBuilder().uri(url1).header("Content-Type", "application/json")
                .POST(body2).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertNotNull(response1, "Нет ответа");
        assertEquals(400, response1.statusCode());
        assertNotNull(response2, "Нет ответа");
        assertEquals(500, response2.statusCode());

    }


}
