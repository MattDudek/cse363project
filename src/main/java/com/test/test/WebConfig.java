package com.test.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.Entity;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}
