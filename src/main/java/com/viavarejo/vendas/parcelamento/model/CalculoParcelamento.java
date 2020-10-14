package com.viavarejo.vendas.parcelamento.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ONE;
import static java.math.MathContext.DECIMAL128;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class CalculoParcelamento {
    private static final BigDecimal CEM = new BigDecimal("100");

    private final BigDecimal valorASerParcelado;
    private final int quantidadeParcelas;

    public CalculoParcelamento(BigDecimal valorASerParcelado, int quantidadeParcelas) {
        this.valorASerParcelado = valorASerParcelado;
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public BigDecimal getValorASerParcelado() {
        return valorASerParcelado;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public List<Parcela> calcularParcelamentoSemJuros() {
        BigDecimal quantidadeParcelasComoBigDecimal = BigDecimal.valueOf(quantidadeParcelas);

        BigDecimal valorParcelaUnicaSemJuros = valorASerParcelado.divide(quantidadeParcelasComoBigDecimal, MathContext.DECIMAL64);

        return gerarParcelasBaseadaEm(valorParcelaUnicaSemJuros, BigDecimal.ZERO);
    }

    /**
     * <pre>
     * Fórmula de cálculo de juros compostos
     *
     * valorParcelaUnicaComJuros =                  valorASerParcelado                              -> numeradorFracao1
     *                                 ------------------------------------------------
     *                                 ((1 + taxaUnitariaJuros) ^ quantidadeParcelas) - 1           -> denominadorFracao1
     *                          -----------------------------------------------------------                                   }  novoDenominadorCalculado
     *                         ((1 + taxaUnitariaJuros) ^ quantidadeParcelas) * taxaUnitariaJuros   -> numeradorFracao2
     *
     *   ^ significa exponenciação
     *   ----- significa divisão
     *
     * </pre>
     */

    public List<Parcela> calcularParcelamentoComTaxaDeJurosDe(BigDecimal taxaPercentualJuros) {
        BigDecimal taxaUnitariaJuros = taxaPercentualJuros.divide(CEM, DECIMAL128);

        BigDecimal umMaisTaxaUnitariaJurosElevadoAQuantidadeParcelas = ONE.add(taxaUnitariaJuros).pow(quantidadeParcelas, DECIMAL128);

        BigDecimal denominadorFracao1 = umMaisTaxaUnitariaJurosElevadoAQuantidadeParcelas.subtract(ONE);
        BigDecimal numeradorFracao2 = umMaisTaxaUnitariaJurosElevadoAQuantidadeParcelas.multiply(taxaUnitariaJuros, DECIMAL128);

        BigDecimal novoDenominadorCalculado = denominadorFracao1.divide(numeradorFracao2, DECIMAL128);

        BigDecimal valorParcelaUnicaComJuros = valorASerParcelado.divide(novoDenominadorCalculado, DECIMAL128);

        return gerarParcelasBaseadaEm(valorParcelaUnicaComJuros, taxaPercentualJuros);
    }

    private List<Parcela> gerarParcelasBaseadaEm(BigDecimal valorParcelaUnica, BigDecimal taxaPercentualJuros) {
        return IntStream.rangeClosed(1, quantidadeParcelas)
                .mapToObj(numeroParcela -> new Parcela(numeroParcela, valorParcelaUnica, taxaPercentualJuros))
                .collect(toList());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
