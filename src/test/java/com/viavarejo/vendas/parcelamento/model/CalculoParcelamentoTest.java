package com.viavarejo.vendas.parcelamento.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculoParcelamentoTest {
    private final static BigDecimal VALOR_A_SER_PARCELADO = new BigDecimal("1000");
    private final static int EM_48_X = 48;
    private final static BigDecimal TAXA_PERCENTUAL_PADRAO_JUROS = new BigDecimal("1.15000");
    private final static int EM_6_X = 6;
    private final static BigDecimal TAXA_PERCENTUAL_SEM_JUROS = new BigDecimal("0.00000");

    private CalculoParcelamento calculoParcelamento;

    @Test
    public void deveCalcularCorretamenteParcelamentoComJurosEm48X() {
        calculoParcelamento = new CalculoParcelamento(VALOR_A_SER_PARCELADO, EM_48_X);

        List<Parcela> parcelas = calculoParcelamento.calcularParcelamentoComTaxaDeJurosDe(TAXA_PERCENTUAL_PADRAO_JUROS);
        Parcela primeiraParcela = parcelas.iterator().next();

        assertAll(
                () -> assertEquals(EM_48_X, parcelas.size()),
                () -> assertEquals(new BigDecimal("27.23"), primeiraParcela.getValor()),
                () -> assertEquals(TAXA_PERCENTUAL_PADRAO_JUROS, primeiraParcela.getTaxaJurosAoMes())
        );
    }

    @Test
    public void deveCalcularCorretamenteParcelamentoSemJurosEm6X() {
        calculoParcelamento = new CalculoParcelamento(VALOR_A_SER_PARCELADO, EM_6_X);

        List<Parcela> parcelas = calculoParcelamento.calcularParcelamentoSemJuros();
        Parcela primeiraParcela = parcelas.iterator().next();

        assertAll(
                () -> assertEquals(EM_6_X, parcelas.size()),
                () -> assertEquals(new BigDecimal("166.67"), primeiraParcela.getValor()),
                () -> assertEquals(TAXA_PERCENTUAL_SEM_JUROS, primeiraParcela.getTaxaJurosAoMes())
        );
    }
}