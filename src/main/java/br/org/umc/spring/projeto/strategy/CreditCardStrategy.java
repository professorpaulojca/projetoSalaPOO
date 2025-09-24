package br.org.umc.spring.projeto.strategy;

import br.org.umc.spring.projeto.interfaces.PaymentStrategy;

import java.math.BigDecimal;

public class CreditCardStrategy implements PaymentStrategy {
    private final String cardLast4;
    private final BigDecimal maxToCharge;

    public CreditCardStrategy(String cardLast4, BigDecimal maxToCharge) {
        this.cardLast4 = cardLast4;
        this.maxToCharge = maxToCharge.max(BigDecimal.ZERO);
    }

    @Override
    public void execute(PaymentContext ctx) {
        if (ctx.remaining.compareTo(BigDecimal.ZERO) <= 0) return;
        BigDecimal charge = ctx.remaining.min(maxToCharge);
        if (charge.compareTo(BigDecimal.ZERO) > 0) {
            ctx.remaining = ctx.remaining.subtract(charge);
            ctx.log("Cartão ****" + cardLast4 + " cobrado: R$ " + charge);
        } else {
            ctx.log("Cartão ****" + cardLast4 + " não cobrado (limite 0).");
        }
    }
}