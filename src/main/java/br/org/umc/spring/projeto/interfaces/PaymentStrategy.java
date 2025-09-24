package br.org.umc.spring.projeto.interfaces;

import br.org.umc.spring.projeto.strategy.PaymentContext;

public interface PaymentStrategy {
    /**
     * Aplica a estratégia sobre o restante a pagar.
     */
    void execute(PaymentContext ctx);
}
