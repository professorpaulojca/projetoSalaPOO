package br.org.umc.spring.projeto.frete;

import br.org.umc.spring.projeto.interfaces.FreteStrategy;

public class Pac implements FreteStrategy {
    public double calcularFrete(double peso) {
        return peso * 8.0;
    }
}
