package com.viavarejo.vendas.parcelamento.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
class EndpointExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

        return new ResponseEntity(erros, BAD_REQUEST);
    }
}
