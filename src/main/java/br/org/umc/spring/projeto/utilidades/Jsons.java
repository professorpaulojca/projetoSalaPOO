package br.org.umc.spring.projeto.utilidades;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Jsons {                                              // classe utilitária
    private static final ObjectMapper MAPPER = new ObjectMapper() // instancia única do mapper
            .findAndRegisterModules()                             // registra módulos (JavaTime, etc.)
            .enable(SerializationFeature.INDENT_OUTPUT);          // ativa identação "bonita"

    public static String toJson(Object o) {                       // método utilitário público
        try {                                                     // tenta serializar
            return MAPPER.writeValueAsString(o);                  // converte o objeto para JSON
        } catch (Exception e) {                                   // em caso de erro
            return "{\"error\":\"" + e.getMessage() + "\"}";      // retorna JSON simples de erro
        }                                                         // fim catch
    }                                                             // fim método
}
