package br.org.umc.spring.projeto.enums;

import java.util.Properties;
import java.util.regex.Pattern;

public enum TipoContato {
    EMAIL("Email", "regex.email", "email.mensagem.erro"),
    TELEFONE("Telefone", "regex.telefone", "telefone.mensagem.erro"),
    CELULAR("Celular", "regex.celular", "celular.mensagem.erro"),
    WHATSAPP("WhatsApp", "regex.whatsapp", "whatsapp.mensagem.erro"),
    LINKEDIN("LinkedIn", "regex.linkedin", "linkedin.mensagem.erro"),
    TELEGRAM("Telegram", "regex.telegram", "telegram.mensagem.erro"),
    SITE("Site", "regex.site", "site.mensagem.erro"),
    OUTRO("Outro", null, null);

    private final String descricao;
    private final String regexProperty;
    private final String mensagemErroProperty;

    TipoContato(String descricao, String regexProperty, String mensagemErroProperty) {
        this.descricao = descricao;
        this.regexProperty = regexProperty;
        this.mensagemErroProperty = mensagemErroProperty;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRegexProperty() {
        return regexProperty;
    }

    public String getMensagemErroProperty() {
        return mensagemErroProperty;
    }

    // Método que recebe as propriedades carregadas externamente
    public boolean isValid(String valor, Properties properties) {
        if (this == OUTRO || regexProperty == null) {
            return true;
        }

        String regexPattern = properties.getProperty(regexProperty);
        if (regexPattern == null || regexPattern.trim().isEmpty()) {
            return true;
        }

        return Pattern.matches(regexPattern, valor);
    }

    // Método para obter mensagem de erro
    public String getMensagemErro(Properties properties) {
        if (mensagemErroProperty == null) {
            return "Valor inválido";
        }
        return properties.getProperty(mensagemErroProperty, "Valor inválido");
    }
}