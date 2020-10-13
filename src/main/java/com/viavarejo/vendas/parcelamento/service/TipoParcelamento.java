package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.DadosEntradaParcelamento;

enum TipoParcelamento {
    SEM_JUROS, COM_JUROS;

    static TipoParcelamento getInstance(DadosEntradaParcelamento dadosEntradaParcelamento) {
        return dadosEntradaParcelamento.getQuantidadeParcelas() <= 6 ? SEM_JUROS : COM_JUROS;
    }
}
