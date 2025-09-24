package br.org.umc.spring.projeto.strategyFrete;

import br.org.umc.spring.projeto.frete.CalculadoraFrete;
import br.org.umc.spring.projeto.frete.Pac;
import br.org.umc.spring.projeto.frete.Sedex;
import org.junit.jupiter.api.Test;

public class FreteTest {

    @Test
    public void testarFrete() {
        CalculadoraFrete calc = new CalculadoraFrete();

        calc.setStrategy(new Sedex());
        System.out.println("Sedex: " + calc.calcular(10));

        calc.setStrategy(new Pac());
        System.out.println("PAC: " + calc.calcular(10));
    }

}
