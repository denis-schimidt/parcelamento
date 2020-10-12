package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.TaxaJuroDiaria;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;

@Service
public class ServicoDePesquisaTaxaSelic {
    private static final ParameterizedTypeReference<List<TaxaJuroDiaria>> TIPO_RESPOSTA_COMO_LISTA_TAXA_JUROS = new ParameterizedTypeReference<List<TaxaJuroDiaria>>(){};

    private final RestOperations restOperations;
    private final String endpointPesquisaTaxaSelic;
    private final Logger logger;

    @Autowired
    ServicoDePesquisaTaxaSelic(RestOperations restOperations, @Qualifier("endpointPesquisaTaxaSelic") String endpointPesquisaTaxaSelic, Logger logger) {
        this.restOperations = restOperations;
        this.endpointPesquisaTaxaSelic = endpointPesquisaTaxaSelic;
        this.logger = logger;
    }

    public Optional<BigDecimal> pesquisarTaxaPercentualNoBancoCentral() {

        try {
            List<TaxaJuroDiaria> taxasJuroDiariasAcumuladas = restOperations.exchange(endpointPesquisaTaxaSelic, GET,
                    null, TIPO_RESPOSTA_COMO_LISTA_TAXA_JUROS).getBody();

            if (taxasJuroDiariasAcumuladas != null && !taxasJuroDiariasAcumuladas.isEmpty()) {
                BigDecimal taxaPercentualJurosSelic = somarTaxasJuroAcumuladas(taxasJuroDiariasAcumuladas);

                logger.info("Taxa Selic obtida do Banco Central -> {} %", taxaPercentualJurosSelic);

                return ofNullable(taxaPercentualJurosSelic);
            }

        } catch (Exception e) {
            logger.error("Erro ao tentar obter a taxa selic atualizada do Banco Central no endpoint: {}", endpointPesquisaTaxaSelic,  e);
        }

        return empty();
    }

    private BigDecimal somarTaxasJuroAcumuladas(List<TaxaJuroDiaria> taxaJuroDiarias) {
        return taxaJuroDiarias
                .stream()
                .map(TaxaJuroDiaria::getTaxaJuros)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
