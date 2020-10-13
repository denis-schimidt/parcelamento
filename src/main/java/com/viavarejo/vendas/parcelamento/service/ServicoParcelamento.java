package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.model.Parcela;
import com.viavarejo.vendas.parcelamento.model.CalculoParcelamento;

import java.util.List;

public interface ServicoParcelamento {

    List<Parcela> calcularParcelamento(CalculoParcelamento calculoParcelamento);

    TipoParcelamento getTipoParcelamento();
}
