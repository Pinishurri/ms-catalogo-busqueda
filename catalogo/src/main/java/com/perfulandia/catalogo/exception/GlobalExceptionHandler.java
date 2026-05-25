package com.perfulandia.catalogo.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Captura todos los errores que ocurran en cualquier Controller
// y los maneja en un solo lugar en vez de repetir el mismo codigo en cada endpoint
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Se activa cuando llegan datos que no pasan las validaciones
    // por ejemplo nombre vacio o precio negativo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeValidacion(
            MethodArgumentNotValidException excepcion) {

        Map<String, String> errores = new HashMap<>();

        // Recorremos cada campo que fallo la validacion y guardamos su mensaje
        excepcion.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        log.error("Errores de validacion encontrados: {}", errores);
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST); // 400
    }

    // Captura cualquier otro error inesperado del sistema
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGeneral(Exception excepcion) {
        log.error("Error inesperado en el sistema: {}", excepcion.getMessage());

        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrio un error inesperado");
        error.put("detalle", excepcion.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}