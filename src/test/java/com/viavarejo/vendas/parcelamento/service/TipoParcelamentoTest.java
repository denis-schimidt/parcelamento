package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.DadosEntradaParcelamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoParcelamentoTest {

    @Mock
    private DadosEntradaParcelamento dadosEntradaParcelamento;

    @Test
    public void deveSelecionarParcelamentoSemJurosParaComprasEmAte6Vezes() {
        when(dadosEntradaParcelamento.getQuantidadeParcelas()).thenReturn(6);

        TipoParcelamento tipoParcelamento = TipoParcelamento.getInstance(dadosEntradaParcelamento);

        assertEquals(TipoParcelamento.SEM_JUROS, tipoParcelamento);
    }

    @Test
    public void deveSelecionarParcelamentoComJurosParaComprasSuperioresA6Vezes() {
        when(dadosEntradaParcelamento.getQuantidadeParcelas()).thenReturn(7);

        TipoParcelamento tipoParcelamento = TipoParcelamento.getInstance(dadosEntradaParcelamento);

        assertEquals(TipoParcelamento.COM_JUROS, tipoParcelamento);
    }
}