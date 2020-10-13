package com.viavarejo.vendas.parcelamento.controller;

import com.viavarejo.vendas.parcelamento.dto.DadosEntradaParcelamento;
import com.viavarejo.vendas.parcelamento.model.Parcela;
import com.viavarejo.vendas.parcelamento.service.SelecionadorServicoParcelamento;
import com.viavarejo.vendas.parcelamento.service.ServicoParcelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("vendas")
class CalculadoraParcelasController {
    private final SelecionadorServicoParcelamento selecionadorServicoDeParcelamento;

    @Autowired
    CalculadoraParcelasController(SelecionadorServicoParcelamento selecionadorServicoDeParcelamento) {
        this.selecionadorServicoDeParcelamento = selecionadorServicoDeParcelamento;
    }

    @PostMapping(value = "/parcelamento", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    List<Parcela> calcularParcelasPor(@Valid @RequestBody DadosEntradaParcelamento dadosEntradaParcelamento) {

        ServicoParcelamento servicoParcelamento = selecionadorServicoDeParcelamento.selecionarServicoBaseadoEm(dadosEntradaParcelamento);

        return servicoParcelamento.calcularParcelamento(dadosEntradaParcelamento.converterParaModelo());
    }
}
