package br.org.umc.spring.projeto.strategy;

import br.org.umc.spring.projeto.interfaces.PaymentStrategy;

import java.math.BigDecimal;

public class StoreBalanceStrategy implements PaymentStrategy {
    private final BigDecimal availableBalance;

    public StoreBalanceStrategy(BigDecimal availableBalance) {
        this.availableBalance = availableBalance.max(BigDecimal.ZERO);
    }

    @Override
    public void execute(PaymentContext ctx) {
        if (ctx.remaining.compareTo(BigDecimal.ZERO) <= 0) return;
        BigDecimal use = ctx.remaining.min(availableBalance);
        if (use.compareTo(BigDecimal.ZERO) > 0) {
            ctx.remaining = ctx.remaining.subtract(use);
            ctx.log("Saldo da conta usado: R$ " + use);
        } else {
            ctx.log("Saldo insuficiente (R$ 0 usado).");
        }
    }
}
