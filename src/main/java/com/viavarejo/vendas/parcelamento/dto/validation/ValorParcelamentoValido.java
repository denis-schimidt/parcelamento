package com.viavarejo.vendas.parcelamento.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValorParcelamentoValidator.class)
@Documented
public @interface ValorParcelamentoValido {

    String message() default "{com.viavarejo.vendas.calculadoraDeParcelamento.dto.validation.ValorFinanciadoValido.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
