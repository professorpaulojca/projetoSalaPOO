package br.org.umc.spring.projeto.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "regex")
public class RegexProperties {
    // Getters e Setters
    private String email;
    private String telefone;
    private String celular;
    private String whatsapp;
    private String linkedin;
    private String telegram;
    private String site;

}