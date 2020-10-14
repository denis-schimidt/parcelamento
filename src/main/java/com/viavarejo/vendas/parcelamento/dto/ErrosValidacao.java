package com.viavarejo.vendas.parcelamento.dto;

import java.util.List;

public class ErrosValidacao {
    private final List<String> erros;

    public ErrosValidacao(List<String> erros) {
        this.erros = erros;
    }

    public List<String> getErros() {
        return erros;
    }
}
