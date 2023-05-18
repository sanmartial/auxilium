package com.globaroman.auxilium.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@ComponentScan(basePackages = "com.globaroman.auxilium")

public class SpringConfig implements WebMvcConfigurer {
}
