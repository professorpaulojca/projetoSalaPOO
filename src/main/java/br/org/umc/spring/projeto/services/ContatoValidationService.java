package br.org.umc.spring.projeto.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.org.umc.spring.projeto.enums.TipoContato;
import java.util.Properties;

@Service
public class ContatoValidationService {

    private final Properties properties;

    public ContatoValidationService(
            @Value("${regex.email}") String emailRegex,
            @Value("${regex.telefone}") String telefoneRegex,
            @Value("${regex.celular}") String celularRegex,
            @Value("${regex.whatsapp}") String whatsappRegex,
            @Value("${regex.telegram}") String telegramRegex,
            @Value("${regex.linkedin}") String linkedinRegex,
            @Value("${regex.site}") String siteRegex,
            @Value("${email.mensagem.erro}") String emailErro,
            @Value("${telefone.mensagem.erro}") String telefoneErro,
            @Value("${celular.mensagem.erro}") String celularErro,
            @Value("${whatsapp.mensagem.erro}") String whatsappErro,
            @Value("${telegram.mensagem.erro}") String telegramErro,
            @Value("${linkedin.mensagem.erro}") String linkedinErro,
            @Value("${site.mensagem.erro}") String siteErro) {

        properties = new Properties();
        properties.setProperty("regex.email", emailRegex);
        properties.setProperty("regex.telefone", telefoneRegex);
        properties.setProperty("regex.celular", celularRegex);
        properties.setProperty("regex.whatsapp", whatsappRegex);
        properties.setProperty("regex.telegram", telegramRegex);
        properties.setProperty("regex.linkedin", linkedinRegex);
        properties.setProperty("regex.site", siteRegex);
        properties.setProperty("email.mensagem.erro", emailErro);
        properties.setProperty("telefone.mensagem.erro", telefoneErro);
        properties.setProperty("celular.mensagem.erro", celularErro);
        properties.setProperty("whatsapp.mensagem.erro", whatsappErro);
        properties.setProperty("telegram.mensagem.erro", telegramErro);
        properties.setProperty("linkedin.mensagem.erro", linkedinErro);
        properties.setProperty("site.mensagem.erro", siteErro);
    }

    public boolean validarContato(TipoContato tipo, String valor) {
        return tipo.isValid(valor, properties);
    }

    public String getMensagemErro(TipoContato tipo) {
        return tipo.getMensagemErro(properties);
    }

    public Properties getProperties() {
        return properties;
    }
}