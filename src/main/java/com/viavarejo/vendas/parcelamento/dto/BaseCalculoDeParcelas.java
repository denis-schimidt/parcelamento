package com.viavarejo.vendas.parcelamento.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.viavarejo.vendas.parcelamento.dto.validation.ValorParcelamentoValido;
import com.viavarejo.vendas.parcelamento.model.CalculadoraDeParcelamento;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
@ValorParcelamentoValido
public class BaseCalculoDeParcelas {

    @Valid
    @NotNull
    private final Produto produto;

    @Valid
    @NotNull
    private final CondicaoPagamento condicaoPagamento;

    @JsonCreator
    BaseCalculoDeParcelas(Produto produto, CondicaoPagamento condicaoPagamento) {
        this.produto = produto;
        this.condicaoPagamento = condicaoPagamento;
    }

    @Hidden
    public int getQuantidadeParcelas() {
        return condicaoPagamento.getQuantidadeDeParcelas();
    }

    @Hidden
    public BigDecimal getValorASerParcelado() {
        return produto.getValor().subtract(condicaoPagamento.getValorDeEntrada());
    }

   public CalculadoraDeParcelamento converterParaModelo() {
        return new CalculadoraDeParcelamento(getValorASerParcelado(), condicaoPagamento.getQuantidadeDeParcelas());
   }

    public Produto getProduto() {
        return produto;
    }

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
