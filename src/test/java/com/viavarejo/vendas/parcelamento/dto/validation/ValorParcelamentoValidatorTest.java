package com.viavarejo.vendas.parcelamento.dto.validation;

import com.viavarejo.vendas.parcelamento.dto.DadosEntradaParcelamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValorParcelamentoValidatorTest {

    @InjectMocks
    private ValorParcelamentoValidator valorParcelamentoValidator;

    @Mock
    private DadosEntradaParcelamento dadosEntradaParcelamento;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void deveRetornarFalseQuandoOValorASerParceladoForNegativo() {
        when(dadosEntradaParcelamento.getValorASerParcelado()).thenReturn(ONE.negate());

        assertFalse(valorParcelamentoValidator.isValid(dadosEntradaParcelamento, constraintValidatorContext));
    }

    @Test
    public void deveRetornarFalseQuandoOValorASerParceladoForZero() {
        when(dadosEntradaParcelamento.getValorASerParcelado()).thenReturn(ZERO);

        assertFalse(valorParcelamentoValidator.isValid(dadosEntradaParcelamento, constraintValidatorContext));
    }

    @Test
    public void deveRetornarTrueQuandoOValorASerParceladoForPositivo() {
        when(dadosEntradaParcelamento.getValorASerParcelado()).thenReturn(ONE);

        assertTrue(valorParcelamentoValidator.isValid(dadosEntradaParcelamento, constraintValidatorContext));
    }
}