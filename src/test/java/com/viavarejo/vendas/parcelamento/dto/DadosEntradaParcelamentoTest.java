package com.viavarejo.vendas.parcelamento.dto;

import com.viavarejo.vendas.parcelamento.model.CalculoParcelamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DadosEntradaParcelamentoTest {

    @Mock
    private Produto produto;
    @Mock
    private CondicaoPagamento condicaoPagamento;

    private DadosEntradaParcelamento dadosEntradaParcelamento;

    @BeforeEach
    public void setUp() {
        when(produto.getValor()).thenReturn(BigDecimal.TEN);
        when(condicaoPagamento.getValorDeEntrada()).thenReturn(BigDecimal.ONE);
        when(condicaoPagamento.getQuantidadeDeParcelas()).thenReturn(10);

        dadosEntradaParcelamento = new DadosEntradaParcelamento(produto, condicaoPagamento);
    }

    @Test
    public void deveConverterDadosParaOModeloCorretamente() {
        CalculoParcelamento calculoParcelamento = dadosEntradaParcelamento.converterParaModelo();

        assertAll(() -> assertEquals(new BigDecimal("9"), calculoParcelamento.getValorASerParcelado()),
                  () -> assertEquals(10, calculoParcelamento.getQuantidadeParcelas()));
    }
}