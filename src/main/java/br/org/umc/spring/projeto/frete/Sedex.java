package br.org.umc.spring.projeto.frete;

import br.org.umc.spring.projeto.interfaces.FreteStrategy;

public class Sedex implements FreteStrategy {
    public double calcularFrete(double peso) {
        return peso * 12.0;
    }
}
