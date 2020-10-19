package com.viavarejo.vendas.parcelamento.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.RoundingMode.HALF_EVEN;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class Parcela {
    private final int numeroParcela;
    private final BigDecimal valor;
    private final BigDecimal taxaJurosAoMes;

    Parcela(int numeroParcela, BigDecimal valor, BigDecimal taxaPercentualJurosMes) {
        this.numeroParcela = numeroParcela;
        this.valor = valor.setScale(2, HALF_EVEN);
        this.taxaJurosAoMes = taxaPercentualJurosMes.setScale(5, HALF_EVEN);
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getTaxaJurosAoMes() {
        return taxaJurosAoMes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parcela parcela = (Parcela) o;

        return numeroParcela == parcela.numeroParcela &&
                Objects.equals(valor, parcela.valor) &&
                Objects.equals(taxaJurosAoMes, parcela.taxaJurosAoMes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroParcela, valor, taxaJurosAoMes);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
