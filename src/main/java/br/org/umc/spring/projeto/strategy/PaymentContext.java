package br.org.umc.spring.projeto.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentContext {
    public final List<String> logs = new ArrayList<>();
    final BigDecimal total;
    public BigDecimal remaining;
    public BigDecimal change = BigDecimal.ZERO; // troco

    public PaymentContext(BigDecimal total) {
        this.total = total;
        this.remaining = total;
    }

    void log(String msg) {
        logs.add(msg);
    }
}
