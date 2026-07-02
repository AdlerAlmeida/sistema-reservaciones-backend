package com.example.SistemaReservaciones.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // <- ¡Este import es clave!
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ManejadorExcepcionesGlobal {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorRespuesta> manejarResourceNotFound(ResourceNotFoundException ex){
        ErrorRespuesta error = new ErrorRespuesta(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorRespuesta> manejarBadRequest(BadRequestException ex){
        ErrorRespuesta error = new ErrorRespuesta(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Petición incorrecta",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ErrorRespuesta> manejarInvalidPriceException(InvalidPriceException ex){
        ErrorRespuesta error = new ErrorRespuesta(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Precio Inválido",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // El interceptor para el @Positive, @NotBlank, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespuesta> manejarValidacionesSpring(MethodArgumentNotValidException ex) {
        // Extrae el mensaje de la anotación de la entidad
        String mensajeDetallado = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorRespuesta error = new ErrorRespuesta(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Validación",
                mensajeDetallado
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}