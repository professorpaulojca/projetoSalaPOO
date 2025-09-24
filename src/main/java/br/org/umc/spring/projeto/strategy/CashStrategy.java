package br.org.umc.spring.projeto.strategy;

import br.org.umc.spring.projeto.interfaces.PaymentStrategy;

import java.math.BigDecimal;

public class CashStrategy implements PaymentStrategy {
    private final BigDecimal cashGiven;

    public CashStrategy(BigDecimal cashGiven) {
        this.cashGiven = cashGiven.max(BigDecimal.ZERO);
    }

    @Override
    public void execute(PaymentContext ctx) {
        if (ctx.remaining.compareTo(BigDecimal.ZERO) <= 0) {
            // Só gera troco se alguém der dinheiro sem necessidade (situação improvável)
            ctx.change = ctx.change.add(cashGiven);
            ctx.log("Dinheiro devolvido (troco redundante): R$ " + cashGiven);
            return;
        }
        if (cashGiven.compareTo(ctx.remaining) >= 0) {
            // Paga tudo e devolve troco
            BigDecimal used = ctx.remaining;
            BigDecimal change = cashGiven.subtract(ctx.remaining);
            ctx.remaining = BigDecimal.ZERO;
            ctx.change = ctx.change.add(change);
            ctx.log("Dinheiro recebido: R$ " + cashGiven + " (usado R$ " + used + ", troco R$ " + change + ")");
        } else {
            // Paga parcialmente
            ctx.remaining = ctx.remaining.subtract(cashGiven);
            ctx.log("Dinheiro utilizado parcialmente: R$ " + cashGiven);
        }
    }
}
