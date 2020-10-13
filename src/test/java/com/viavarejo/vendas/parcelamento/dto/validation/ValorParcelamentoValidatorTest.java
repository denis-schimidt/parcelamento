package com.viavarejo.vendas.parcelamento.dto.validation;

import com.viavarejo.vendas.parcelamento.dto.DadosEntradaParcelamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValorParcelamentoValidatorTest {

    @InjectMocks
    private ValorParcelamentoValidator valorParcelamentoValidator;

    @Mock
    private DadosEntradaParcelamento dadosEntradaParcelamento;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test // Valor da entrada maior que valor do produto
    public void deveRetornarFalseQuandoOValorParceladoForNegativo() {
        when(dadosEntradaParcelamento.getValorASerParcelado()).thenReturn(BigDecimal.ONE.negate());

        assertFalse(valorParcelamentoValidator.isValid(dadosEntradaParcelamento, constraintValidatorContext));
    }

//    @Test // Valor da entrada maior que valor do produto
//    public void deveRetornarFalseQuandoOValorParceladoForNegativo() {
//        when(baseCalculoDeParcelas.getValorASerParcelado()).thenReturn(BigDecimal.ZERO);
//
//        assertFalse(valorFinanciadoValidator.isValid(baseCalculoDeParcelas, constraintValidatorContext));
//    }
}