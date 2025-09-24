package br.org.umc.spring.projeto.frete;

import br.org.umc.spring.projeto.interfaces.FreteStrategy;

public class CalculadoraFrete {
    private FreteStrategy strategy;

    public void setStrategy(FreteStrategy strategy) {
        this.strategy = strategy;
    }

    public double calcular(double peso) {

        return strategy.calcularFrete(peso);
    }
}
