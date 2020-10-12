package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.BaseCalculoDeParcelas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoParcelamentoTest {

    @Mock
    private BaseCalculoDeParcelas baseCalculoDeParcelas;

    @Test
    public void deveSelecionarParcelamentoSemJurosParaComprasEmAte6Vezes() {
        when(baseCalculoDeParcelas.getQuantidadeParcelas()).thenReturn(6);

        TipoParcelamento tipoParcelamento = TipoParcelamento.getInstance(baseCalculoDeParcelas);

        assertEquals(TipoParcelamento.SEM_JUROS, tipoParcelamento);
    }

    @Test
    public void deveSelecionarParcelamentoComJurosParaComprasSuperioresA6Vezes() {
        when(baseCalculoDeParcelas.getQuantidadeParcelas()).thenReturn(7);

        TipoParcelamento tipoParcelamento = TipoParcelamento.getInstance(baseCalculoDeParcelas);

        assertEquals(TipoParcelamento.COM_JUROS, tipoParcelamento);
    }
}