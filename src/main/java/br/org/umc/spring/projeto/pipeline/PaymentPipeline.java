package br.org.umc.spring.projeto.pipeline;

import br.org.umc.spring.projeto.interfaces.PaymentStrategy;
import br.org.umc.spring.projeto.strategy.PaymentContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentPipeline {
    private final List<PaymentStrategy> steps = new ArrayList<>();

    public PaymentPipeline addStep(PaymentStrategy step) {
        steps.add(step);
        return this;
    }

    public PaymentContext execute(BigDecimal total) {
        PaymentContext ctx = new PaymentContext(total);
        for (PaymentStrategy s : steps) {
            if (ctx.remaining.compareTo(BigDecimal.ZERO) == 0) break;
            s.execute(ctx);
        }
        return ctx;
    }
}