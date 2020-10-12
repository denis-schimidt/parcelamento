package com.viavarejo.vendas.parcelamento.service;

import com.viavarejo.vendas.parcelamento.dto.BaseCalculoDeParcelas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

@Component
public class SelecionadorServicoParcelamento {
    private final EnumMap<TipoParcelamento, ServicoParcelamento> simuladorParcelasPorTipoParcelamento = new EnumMap<>(TipoParcelamento.class);

    @Autowired
    SelecionadorServicoParcelamento(List<ServicoParcelamento> simuladorParcelas) {
        for (ServicoParcelamento servicoParcelamento : simuladorParcelas) {
            simuladorParcelasPorTipoParcelamento.putIfAbsent(servicoParcelamento.getTipoParcelamento(), servicoParcelamento);
        }
    }

    public ServicoParcelamento selecionarServicoBaseadoEm(BaseCalculoDeParcelas baseCalculoDeParcelas) {
        TipoParcelamento tipoParcelamento = TipoParcelamento.getInstance(baseCalculoDeParcelas);

        return simuladorParcelasPorTipoParcelamento.get(tipoParcelamento);
    }
}
