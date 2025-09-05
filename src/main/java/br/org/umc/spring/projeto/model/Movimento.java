package br.org.umc.spring.projeto.model;

import java.sql.Timestamp;
import br.org.umc.spring.projeto.enums.TipoDeMovimento;

public class Movimento {
    private Long id;
    private Produto produto;
    private Timestamp dataHora;
    private TipoDeMovimento tipoDeMovimento;
    private Double quantidade;
    private String referencia;
    private Long idReferencia;
}
