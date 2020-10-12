package com.viavarejo.vendas.parcelamento.dto;

import com.viavarejo.vendas.parcelamento.model.CalculadoraDeParcelamento;
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
class BaseCalculoDeParcelasTest {

    @Mock
    private Produto produto;
    @Mock
    private CondicaoPagamento condicaoPagamento;

    private BaseCalculoDeParcelas baseCalculoDeParcelas;

    @BeforeEach
    public void setUp() {
        when(produto.getValor()).thenReturn(BigDecimal.TEN);
        when(condicaoPagamento.getValorDeEntrada()).thenReturn(BigDecimal.ONE);
        when(condicaoPagamento.getQuantidadeDeParcelas()).thenReturn(10);

        baseCalculoDeParcelas = new BaseCalculoDeParcelas(produto, condicaoPagamento);
    }

    @Test
    public void deveConverterDadosParaOModeloCorretamente() {
        CalculadoraDeParcelamento calculadoraDeParcelamento = baseCalculoDeParcelas.converterParaModelo();

        assertAll(() -> assertEquals(new BigDecimal("9"), calculadoraDeParcelamento.getValorASerParcelado()),
                  () -> assertEquals(10, calculadoraDeParcelamento.getQuantidadeParcelas()));
    }
}