package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.model.CalculoParcelamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServicoParcelamentoSemJurosTest {

    @InjectMocks
    private ServicoParcelamentoSemJuros servicoParcelamentoSemJuros;

    @Mock
    private CalculoParcelamento calculoParcelamento;

    @Test
    public void deveChamarCalculadoraDeParcelamentoCorretamente() {
        servicoParcelamentoSemJuros.calcularParcelamento(calculoParcelamento);

        verify(calculoParcelamento).calcularParcelamentoSemJuros();
    }

    @Test
    public void deveRetornarTipoParcelamentoComJuros() {
        assertEquals(TipoParcelamento.SEM_JUROS, servicoParcelamentoSemJuros.getTipoParcelamento());
    }
}