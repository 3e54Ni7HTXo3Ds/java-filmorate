# java-filmorate
Template repository for Filmorate project.

![ER-diagram](https://github.com/3e54Ni7HTXo3Ds/java-filmorate/blob/er-diagramm/src/main/resources/static/db_er_diagram.png)

Основные запросы

Вывод всех пользователей:
SELECT *
FROM user

Вывод всех фильмов
SELECT *
FROM film

Топ N наиболее популярных фильмов

SELECT
film_id,
COUNT(user_id) as count
FROM film_likes
GROUP BY film_id
ORDER BY count desc
LIMIT N

Cписок общих друзей с другим пользователем (пока без фильтрации и уникальности)
(между 1 и 2)
SELECT
user_request_id as req,
user_accept_id as ac
FROM friendship
WHERE req in (1,2) OR acc in (1,2)