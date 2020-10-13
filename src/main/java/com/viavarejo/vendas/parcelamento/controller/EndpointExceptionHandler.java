package com.viavarejo.vendas.parcelamento.controller;

import com.viavarejo.vendas.parcelamento.dto.ErrosValidacao;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
class EndpointExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "Para valores inválidos fornecidos pelo usuário", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation=ErrosValidacao.class)))
    ResponseEntity handleValidationExceptions(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<String> erros = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + " -> " + error.getDefaultMessage())
                .collect(toList());

        if (erros.isEmpty()) {
            erros.addAll(result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(toList()));
        }

        return new ResponseEntity(new ErrosValidacao(erros), BAD_REQUEST);
    }
}
