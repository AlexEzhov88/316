package com.example.resttemplate_316;

import com.example.resttemplate_316.Service.RestTemplateService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Создание объекта сервиса RestTemplateService с новым экземпляром RestTemplate
        RestTemplateService restTemplateService = new RestTemplateService(new RestTemplate());

        // Вызов метода getUserWithCookie() на созданном объекте сервиса
        restTemplateService.getUserWithCookie();
    }

    // Конфигурация RestTemplate как бина в Spring контексте
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}