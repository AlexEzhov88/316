package com.example.resttemplate_316.Service;

import com.example.resttemplate_316.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Service
public class RestTemplateService {

    // создаем экземпляр RestTemplate
    private final RestTemplate restTemplate;

    // URL адрес для взаимодействия с API пользователей
    private static final String USERS_API_URL = "http://94.198.50.185:7081/api/users";

    // конструктор класса
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // метод для выполнения последовательности действий с использованием API пользователей
    public void getUserWithCookie() {
        // Создание экземпляра HttpHeaders и установка заголовка Content-Type
        HttpHeaders headers = createHeadersWithContentType();

        // Получение списка пользователей с API и извлечение заголовка Set-Cookie для авторизации
        ResponseEntity<List<User>> responseEntity = getUsersList(headers);
        extractAndSetCookieHeader(headers, responseEntity);

        // Создание нового пользователя и установка заголовков для авторизации
        User newUser = createUserObject();
        HttpEntity<User> httpEntity = createHttpEntityWithHeaders(newUser, headers);

        // Добавление нового пользователя с использованием API и получение ответа от сервера
        String savedUserResponse = saveNewUser(httpEntity);

        // Обновление информации о пользователе и отправка измененных данных на сервер
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        httpEntity = createHttpEntityWithHeaders(newUser, headers);
        String updatedUserResponse = updateUser(httpEntity);

        // Удаление пользователя с использованием API и получение ответа от сервера
        String deletedUserResponse = deleteUser(httpEntity, newUser);

        // Вывод результатов действий в консоль
        System.out.println(savedUserResponse + updatedUserResponse + deletedUserResponse);
    }

    // вспомогательный метод для создания объекта HttpHeaders с установленным заголовком Content-Type
    private HttpHeaders createHeadersWithContentType() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    // вспомогательный метод для получения списка пользователей с использованием API
    private ResponseEntity<List<User>> getUsersList(HttpHeaders headers) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(USERS_API_URL, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
    }

    // вспомогательный метод для создания нового объекта пользователя
    private User createUserObject() {
        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 35);
        return newUser;
    }

    // вспомогательный метод для создания объекта HttpEntity с установленными заголовками HttpHeaders
    private HttpEntity<User> createHttpEntityWithHeaders(User user, HttpHeaders headers) {
        return new HttpEntity<>(user, headers);
    }

    // вспомогательный метод для извлечения заголовка Set-Cookie и установки его в заголовок Cookie для авторизации
    // Извлекает и устанавливает заголовок Cookie в headers, если он присутствует в ответе сервера
    private void extractAndSetCookieHeader(HttpHeaders headers, ResponseEntity<?> responseEntity) {
        if (responseEntity != null) {
            // Извлечение заголовков из ответа сервера
            responseEntity.getHeaders();
            // Извлечение значения заголовка Set-Cookie
            List<String> setCookieHeaders = responseEntity.getHeaders().get("Set-Cookie");
            if (setCookieHeaders != null && !setCookieHeaders.isEmpty()) {
                // Установка значения заголовка Cookie в headers
                headers.set("Cookie", String.join(";", setCookieHeaders));
            }
        }
    }

    // Отправляет POST-запрос на сервер с объектом User в теле запроса, используя объект HttpEntity с установленными заголовками
    private String saveNewUser(HttpEntity<User> httpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(USERS_API_URL, HttpMethod.POST, httpEntity, String.class);
        return responseEntity.getBody();
    }

    // Отправляет PUT-запрос на сервер с объектом User в теле запроса, используя объект HttpEntity с установленными заголовками
    private String updateUser(HttpEntity<User> httpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(USERS_API_URL, HttpMethod.PUT, httpEntity, String.class);
        return responseEntity.getBody();
    }

    // Отправляет DELETE-запрос на сервер по заданному пути, используя объект HttpEntity с установленными заголовками
    private String deleteUser(HttpEntity<User> httpEntity, User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(USERS_API_URL + "/" + user.getId(), HttpMethod.DELETE, httpEntity, String.class);
        return responseEntity.getBody();
    }
}