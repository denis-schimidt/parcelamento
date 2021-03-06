package com.viavarejo.vendas.parcelamento.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CondicaoPagamento {

    @NotNull @DecimalMin("0.00")
    private final BigDecimal valorDeEntrada;

    @NotNull @Min(1) @Max(48)
    private final Integer quantidadeDeParcelas;

    @JsonCreator
    CondicaoPagamento(@JsonProperty("valorEntrada") BigDecimal valorDeEntrada, @JsonProperty("qtdeParcelas") Integer quantidadeDeParcelas) {
        this.valorDeEntrada = valorDeEntrada;
        this.quantidadeDeParcelas = quantidadeDeParcelas;
    }

    @JsonProperty("valorEntrada")
    public BigDecimal getValorDeEntrada() {
        return valorDeEntrada;
    }

    @JsonProperty("qtdeParcelas")
    public Integer getQuantidadeDeParcelas() {
        return quantidadeDeParcelas;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
