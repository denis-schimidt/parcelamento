package com.viavarejo.vendas.parcelamento.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto {

    @NotNull @Min(1)
    private final Long codigo;

    @NotBlank
    private final String nome;

    @NotNull @DecimalMin("1.00")
    private final BigDecimal valor;

    @JsonCreator
    Produto(Long codigo, String nome, BigDecimal valor) {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
