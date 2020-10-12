package com.viavarejo.vendas.parcelamento.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class Parcela {
    private final int numeroParcela;
    private final BigDecimal valor;
    private final BigDecimal taxaJurosMes;

    Parcela(int numeroParcela, BigDecimal valor, BigDecimal taxaPercentualJurosMes) {
        this.numeroParcela = numeroParcela;
        this.valor = valor.setScale(2, HALF_EVEN);
        this.taxaJurosMes = taxaPercentualJurosMes.setScale(5, HALF_EVEN);
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getTaxaJurosMes() {
        return taxaJurosMes;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
