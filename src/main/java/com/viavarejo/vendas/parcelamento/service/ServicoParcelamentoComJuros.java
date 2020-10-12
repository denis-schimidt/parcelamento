package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.model.Parcela;
import com.viavarejo.vendas.parcelamento.model.CalculadoraDeParcelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.viavarejo.vendas.parcelamento.service.TipoParcelamento.COM_JUROS;

@Service
class ServicoParcelamentoComJuros implements ServicoParcelamento {
    private final BigDecimal valorPercentualSelicPadrao;
    private final ServicoDePesquisaTaxaSelic servicoDePesquisaTaxaSelic;
    private final boolean servicoPesquisaHabilitado;

    @Autowired
    ServicoParcelamentoComJuros(@Value("${selic.taxa-percentual-padrao}") BigDecimal valorPercentualSelicPadrao, ServicoDePesquisaTaxaSelic servicoDePesquisaTaxaSelic,
                                @Value("${selic.servico-pesquisa.habilitado}") boolean servicoPesquisaHabilitado) {

        this.valorPercentualSelicPadrao = valorPercentualSelicPadrao;
        this.servicoDePesquisaTaxaSelic = servicoDePesquisaTaxaSelic;
        this.servicoPesquisaHabilitado = servicoPesquisaHabilitado;
    }

    @Override
    public List<Parcela> calcularParcelamento(final CalculadoraDeParcelamento calculadoraDeParcelamento) {
        return calculadoraDeParcelamento.calcularParcelamentoComTaxaDeJurosDe(obterTaxaJurosSelic());
    }

    @Override
    public TipoParcelamento getTipoParcelamento() {
        return COM_JUROS;
    }

    private BigDecimal obterTaxaJurosSelic() {

        if (servicoPesquisaHabilitado) {
            return servicoDePesquisaTaxaSelic.pesquisarTaxaPercentualNoBancoCentral().orElse(valorPercentualSelicPadrao);
        }

        return valorPercentualSelicPadrao;
    }
}
