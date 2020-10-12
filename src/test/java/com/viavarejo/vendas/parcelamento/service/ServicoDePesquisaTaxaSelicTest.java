package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.TaxaJuroDiaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class ServicoDePesquisaTaxaSelicTest {
    private static final ParameterizedTypeReference<List<TaxaJuroDiaria>> TIPO_RESPOSTA_COMO_LISTA_TAXA_JUROS = new ParameterizedTypeReference<List<TaxaJuroDiaria>>(){};
    private static final String ENDPOINT = "endpoint";

    @Mock
    private RestOperations restOperations;
    @Mock
    private Logger logger;

    private ServicoDePesquisaTaxaSelic servicoDePesquisaTaxaSelic;

    @Mock
    private ResponseEntity<List<TaxaJuroDiaria>> responseEntity;

    @BeforeEach
    public void setUp() {
        when(restOperations.exchange(ENDPOINT, GET, null, TIPO_RESPOSTA_COMO_LISTA_TAXA_JUROS)).thenReturn(responseEntity);

        servicoDePesquisaTaxaSelic = new ServicoDePesquisaTaxaSelic(restOperations, ENDPOINT, logger);
    }

    @Test
    public void deveLogarERetornarEmptyQuandoUmaExcecaoOcorrerAoRealizarAPesquisa() {
        when(responseEntity.getBody()).thenThrow(new RuntimeException());

        Optional<BigDecimal> optionalBigDecimal = servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral();

        verify(logger).error(anyString(), anyString(), any(Exception.class));
        assertFalse(optionalBigDecimal.isPresent());
    }

    @Test
    public void deveRetornarEmptyQuandoOResultadoDaPesquisaForNulo() {
        when(responseEntity.getBody()).thenReturn(null);

        Optional<BigDecimal> optionalBigDecimal = servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral();

        assertFalse(optionalBigDecimal.isPresent());
    }

    @Test
    public void deveRetornarEmptyQuandoOResultadoDaPesquisaForUmaListaVazia() {
        when(responseEntity.getBody()).thenReturn(new ArrayList<>());

        Optional<BigDecimal> optionalBigDecimal = servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral();

        assertFalse(optionalBigDecimal.isPresent());
    }

    @Test
    public void deveObterTaxaJurosDoBancoCentralSomandoOsValoresRecebidosQuandoOServicoPesquisaRetornarValoresValidos() {
        when(responseEntity.getBody()).thenReturn(getTaxaJurosDiarias());

        BigDecimal taxaJurosCalculada = servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral().get();

        assertEquals(new BigDecimal("0.27"), taxaJurosCalculada);
    }

    private List<TaxaJuroDiaria> getTaxaJurosDiarias() {
        List<TaxaJuroDiaria> taxaJuroDiarias = new ArrayList<>();
        taxaJuroDiarias.add(new TaxaJuroDiaria(LocalDate.now(), new BigDecimal("0.12")));
        taxaJuroDiarias.add(new TaxaJuroDiaria(LocalDate.now(), new BigDecimal("0.15")));

        return taxaJuroDiarias;
    }
}