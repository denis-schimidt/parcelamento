package com.viavarejo.vendas.parcelamento.dto.validation;

import com.viavarejo.vendas.parcelamento.dto.DadosEntradaParcelamento;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.math.BigDecimal.ZERO;

public class ValorParcelamentoValidator implements ConstraintValidator<ValorParcelamentoValido, DadosEntradaParcelamento> {

    @Override
    public boolean isValid(DadosEntradaParcelamento dadosEntrada, ConstraintValidatorContext constraintContext) {

        if (dadosEntrada.getValorASerParcelado().compareTo(ZERO) <= 0) {
            return false;
        }

        return true;
    }
}
