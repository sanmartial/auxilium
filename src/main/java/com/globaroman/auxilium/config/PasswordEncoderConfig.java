package com.globaroman.auxilium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();    }
    //Метод passwordEncoder() возвращает экземпляр BCryptPasswordEncoder, который является реализацией PasswordEncoder.
    // PasswordEncoder это интерфейс в Spring Security, который используется для кодирования паролей.
    //Аннотация @Bean указывает, что метод должен быть вызван для создания объекта, который будет управляться Spring Container.
    //BCryptPasswordEncoder - это реализация, которая использует шифрование BCrypt.
    // BCrypt является сильной функцией хеширования, которая основана на Blowfish.
    // Одной из особенностей BCrypt является то, что он автоматически генерирует "соль" (случайный набор байтов, добавленный к паролю перед хешированием),
    // что делает его очень безопасным для хранения паролей.
    //В общем, этот метод предоставляет механизм для безопасного хеширования паролей в вашем приложении.
}
