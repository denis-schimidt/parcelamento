package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.model.Parcela;
import com.viavarejo.vendas.parcelamento.model.CalculoParcelamento;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.viavarejo.vendas.parcelamento.service.TipoParcelamento.SEM_JUROS;

@Service
class ServicoParcelamentoSemJuros implements ServicoParcelamento {

    @Override
    public List<Parcela> calcularParcelamento(final CalculoParcelamento calculoParcelamento) {
        return calculoParcelamento.calcularParcelamentoSemJuros();
    }

    @Override
    public TipoParcelamento getTipoParcelamento() {
        return SEM_JUROS;
    }
}
