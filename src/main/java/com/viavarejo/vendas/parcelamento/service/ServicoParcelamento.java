package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.model.Parcela;
import com.viavarejo.vendas.parcelamento.model.CalculadoraDeParcelamento;

import java.util.List;

public interface ServicoParcelamento {

    List<Parcela> calcularParcelamento(CalculadoraDeParcelamento calculadoraDeParcelamento);

    TipoParcelamento getTipoParcelamento();
}
