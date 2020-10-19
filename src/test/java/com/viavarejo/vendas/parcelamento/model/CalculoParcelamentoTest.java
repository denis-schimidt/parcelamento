package com.viavarejo.vendas.parcelamento.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class CalculoParcelamentoTest {
    private final static BigDecimal VALOR_A_SER_PARCELADO = new BigDecimal("1000");
    private final static int EM_48_X = 48;
    private final static BigDecimal TAXA_PERCENTUAL_PADRAO_JUROS = new BigDecimal("1.15000");
    private final static int EM_6_X = 6;

    private CalculoParcelamento calculoParcelamento;

    @Test
    public void deveCalcularCorretamenteParcelamentoComJurosEm48X() {
        calculoParcelamento = new CalculoParcelamento(VALOR_A_SER_PARCELADO, EM_48_X);

        List<Parcela> parcelasCriadas = calculoParcelamento.calcularParcelamentoComTaxaDeJurosDe(TAXA_PERCENTUAL_PADRAO_JUROS);

        List<Parcela> parcelasEsperadas = gerarListaParcelasEsperadas(EM_48_X, new BigDecimal("27.23"), TAXA_PERCENTUAL_PADRAO_JUROS);
        assertAll(
                () -> assertEquals(EM_48_X, parcelasCriadas.size()),
                () -> assertIterableEquals(parcelasEsperadas,  parcelasCriadas)
        );
    }

    @Test
    public void deveCalcularCorretamenteParcelamentoSemJurosEm6X() {
        calculoParcelamento = new CalculoParcelamento(VALOR_A_SER_PARCELADO, EM_6_X);

        List<Parcela> parcelasCriadas = calculoParcelamento.calcularParcelamentoSemJuros();

        List<Parcela> parcelasEsperadas = gerarListaParcelasEsperadas(EM_6_X, new BigDecimal("166.67"), ZERO);
        assertAll(
                () -> assertEquals(EM_6_X, parcelasCriadas.size()),
                () -> assertIterableEquals(parcelasEsperadas,  parcelasCriadas)
        );
    }

    private List<Parcela> gerarListaParcelasEsperadas(int quantidadeParcelas, BigDecimal valor, BigDecimal taxaPercentualJuros) {
        return IntStream.range(0, quantidadeParcelas)
            .mapToObj(indice -> new Parcela(indice + 1, valor, taxaPercentualJuros))
            .collect(toList());
    }
}
