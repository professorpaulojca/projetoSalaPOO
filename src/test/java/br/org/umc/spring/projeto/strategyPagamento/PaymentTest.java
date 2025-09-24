package br.org.umc.spring.projeto.strategyPagamento;

import br.org.umc.spring.projeto.pipeline.PaymentPipeline;
import br.org.umc.spring.projeto.strategy.CashStrategy;
import br.org.umc.spring.projeto.strategy.CreditCardStrategy;
import br.org.umc.spring.projeto.strategy.PaymentContext;
import br.org.umc.spring.projeto.strategy.StoreBalanceStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PaymentTest {
    private static void demo(String title, PaymentPipeline pipeline, BigDecimal total) {
        System.out.println("\n=== " + title + " ===");
        PaymentContext ctx = pipeline.execute(total);
        ctx.logs.forEach(System.out::println);
        System.out.println("Total: R$ " + total);
        System.out.println("Restante: R$ " + ctx.remaining);
        System.out.println("Troco: R$ " + ctx.change);
        System.out.println(ctx.remaining.compareTo(BigDecimal.ZERO) == 0 ? "✅ Pago" : "❌ Faltou pagar");
    }

    @Test
    public void testarPagamento() {
        // Caso 1: dois cartões de crédito (ordem importa)
        demo("Dois cartões",
                new PaymentPipeline()
                        .addStep(new CreditCardStrategy("1111", new BigDecimal("200.00")))
                        .addStep(new CreditCardStrategy("2222", new BigDecimal("400.00"))),
                new BigDecimal("350.00")
        );

        // Caso 2: cartão + dinheiro (troco)
        demo("Cartão + dinheiro (com troco)",
                new PaymentPipeline()
                        .addStep(new CreditCardStrategy("5555", new BigDecimal("180.00")))
                        .addStep(new CashStrategy(new BigDecimal("250.00"))),
                new BigDecimal("200.00")
        );

        // Caso 3: cartão + dinheiro + saldo
        demo("Cartão + dinheiro + saldo",
                new PaymentPipeline()
                        .addStep(new CreditCardStrategy("9999", new BigDecimal("100.00")))
                        .addStep(new CashStrategy(new BigDecimal("120.00")))
                        .addStep(new StoreBalanceStrategy(new BigDecimal("200.00"))),
                new BigDecimal("290.00")
        );

        // Caso 4: insuficiente (mostra quanto faltou)
        demo("Pagamento insuficiente",
                new PaymentPipeline()
                        .addStep(new CreditCardStrategy("1234", new BigDecimal("50.00")))
                        .addStep(new StoreBalanceStrategy(new BigDecimal("20.00"))),
                new BigDecimal("100.00")
        );
    }
}
