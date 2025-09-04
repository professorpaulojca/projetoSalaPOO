package br.org.umc.spring.projeto.model;

import br.org.umc.spring.projeto.enums.TipoContato;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contato {
    @With
    private Long id;

    private TipoContato tipo;
    private String valor;

    @Builder.Default
    private boolean principal = false;

    @Builder.Default
    private boolean ativo = true;

    // Construtor personalizado para o Builder
    @Builder
    public Contato(TipoContato tipo, String valor, boolean principal, boolean ativo) {
        this.id = System.currentTimeMillis();
        this.tipo = tipo;
        this.valor = valor;
        this.principal = principal;
        this.ativo = ativo;
    }
}
