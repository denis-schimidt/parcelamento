package com.viavarejo.vendas.parcelamento.dto;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class TaxaJuroDiaria {
    private final LocalDate data;
    private final BigDecimal taxaJuros;

    @JsonCreator
    public TaxaJuroDiaria(@JsonProperty("data") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data, @JsonProperty("valor") BigDecimal taxaJuros) {
        this.data = data;
        this.taxaJuros = taxaJuros;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
