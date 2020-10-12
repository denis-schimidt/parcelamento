package com.viavarejo.vendas.parcelamento.dto.validation;

import com.viavarejo.vendas.parcelamento.dto.BaseCalculoDeParcelas;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.math.BigDecimal.ZERO;

public class ValorParcelamentoValidator implements ConstraintValidator<ValorParcelamentoValido, BaseCalculoDeParcelas> {

    @Override
    public boolean isValid(BaseCalculoDeParcelas baseCalculo, ConstraintValidatorContext constraintContext) {

        int valorParceladoComparadoAZero = baseCalculo.getValorASerParcelado().compareTo(ZERO);

        if(valorParceladoComparadoAZero < 0 || valorParceladoComparadoAZero == 0) {
            return false;
        }

        return true;
    }
}
