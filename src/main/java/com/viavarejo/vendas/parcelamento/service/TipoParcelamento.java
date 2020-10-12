package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.BaseCalculoDeParcelas;

enum TipoParcelamento {
    SEM_JUROS, COM_JUROS;

    static TipoParcelamento getInstance(BaseCalculoDeParcelas baseCalculoDeParcelas) {
        return baseCalculoDeParcelas.getQuantidadeParcelas() <= 6 ? SEM_JUROS : COM_JUROS;
    }
}
