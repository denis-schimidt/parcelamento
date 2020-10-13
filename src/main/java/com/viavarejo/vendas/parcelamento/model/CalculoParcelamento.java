package com.viavarejo.vendas.parcelamento.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ONE;
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

    public List<Parcela> calcularParcelamentoComTaxaDeJurosDe(BigDecimal taxaPercentualJuros) {
        BigDecimal taxaUnitariaJuros = taxaPercentualJuros.divide(CEM, MathContext.DECIMAL64);
        BigDecimal jurosTotal = ONE.add(taxaUnitariaJuros).pow(quantidadeParcelas);

        BigDecimal valorParcelaUnicaComJuros = valorASerParcelado.multiply(
                jurosTotal.multiply(taxaUnitariaJuros)
                        .divide(jurosTotal.subtract(ONE), MathContext.DECIMAL64));

        return gerarParcelasBaseadaEm(valorParcelaUnicaComJuros, taxaPercentualJuros);
    }

    private List<Parcela> gerarParcelasBaseadaEm(BigDecimal valorParcelaUnica, BigDecimal taxaJuros) {
        return IntStream.rangeClosed(1, quantidadeParcelas)
                .mapToObj(numeroParcela -> new Parcela(numeroParcela, valorParcelaUnica, taxaJuros))
                .collect(toList());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
