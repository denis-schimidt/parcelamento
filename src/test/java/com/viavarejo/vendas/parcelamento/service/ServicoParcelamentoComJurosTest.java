package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.model.CalculadoraDeParcelamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicoParcelamentoComJurosTest {
    private static final BigDecimal TAXA_SELIC_BANCO_CENTRAL = new BigDecimal("0.20");
    private static final BigDecimal TAXA_SELIC_PADRAO = new BigDecimal("1.15");

    @Mock
    private ServicoDePesquisaTaxaSelic servicoDePesquisaTaxaSelic;

    private ServicoParcelamentoComJuros servicoParcelamentoComJuros;

    @Mock
    private CalculadoraDeParcelamento calculadoraDeParcelamento;
    @Captor
    private ArgumentCaptor<BigDecimal> taxaSelicCaptor;

    @Test
    public void deveChamarCalculadoraDeParcelamentoComTaxaSelicPadraoQuandoOServicoPesquisaEstiverDesabilitado() {
        servicoParcelamentoComJuros = new ServicoParcelamentoComJuros(TAXA_SELIC_PADRAO, servicoDePesquisaTaxaSelic, false);

        servicoParcelamentoComJuros.calcularParcelamento(calculadoraDeParcelamento);

        verify(calculadoraDeParcelamento).calcularParcelamentoComTaxaDeJurosDe(TAXA_SELIC_PADRAO);
    }

    @Test
    public void deveChamarCalculadoraDeParcelamentoComTaxaSelicPadraoQuandoOServicoPesquisaEstiverHabilitadoPoremRetornaVazio() {
        servicoParcelamentoComJuros = new ServicoParcelamentoComJuros(TAXA_SELIC_PADRAO, servicoDePesquisaTaxaSelic, true);
        when(servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral()).thenReturn(empty());

        servicoParcelamentoComJuros.calcularParcelamento(calculadoraDeParcelamento);

        verify(calculadoraDeParcelamento).calcularParcelamentoComTaxaDeJurosDe(TAXA_SELIC_PADRAO);
    }

    @Test
    public void deveChamarCalculadoraDeParcelamentoComTaxaSelicDoBancoCentralQuandoServicoPesquisaEstiverHabilitado() {
        servicoParcelamentoComJuros = new ServicoParcelamentoComJuros(TAXA_SELIC_PADRAO, servicoDePesquisaTaxaSelic, true);
        when(servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral()).thenReturn(of(TAXA_SELIC_BANCO_CENTRAL));

        servicoParcelamentoComJuros.calcularParcelamento(calculadoraDeParcelamento);

        verify(calculadoraDeParcelamento).calcularParcelamentoComTaxaDeJurosDe(taxaSelicCaptor.capture());
        assertEquals(TAXA_SELIC_BANCO_CENTRAL, taxaSelicCaptor.getValue());
    }

    @Test
    public void deveRetornarTipoParcelamentoComJuros() {
        servicoParcelamentoComJuros = new ServicoParcelamentoComJuros(TAXA_SELIC_PADRAO, servicoDePesquisaTaxaSelic, true);

        assertEquals(TipoParcelamento.COM_JUROS, servicoParcelamentoComJuros.getTipoParcelamento());
    }
}