package com.kreitify.api.infraestructure.rest.exception;

import com.kreitify.api.application.dto.ApiError;
import com.kreitify.api.domain.exception.EntityInUseException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern DUP_KEY_PATTERN = Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\)");

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message, Object errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        if (errors != null) {
            body.put("errors", errors);
        }
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonErrors(HttpMessageNotReadableException ex) {
        String message = "El formato de los datos enviados no es válido.";

        if (ex.getMessage().contains("java.time.LocalDate")) {
            message = "La fecha no es válida. El formato correcto debe ser 'yyyy-MM-dd'.";
        }

        return buildResponse(HttpStatus.BAD_REQUEST, message, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String fieldName = field.substring(0, 1).toUpperCase() + field.substring(1);
            String annotation = error.getCode();
            String message;
            if ("Size".equals(annotation)) {
                Object min = error.getArguments()[2];
                Object max = error.getArguments()[1];
                message = "debe tener entre " + min + " y " + max + " caracteres";
            } else {
                message = switch (annotation != null ? annotation : "") {
                    case "NotBlank", "NotNull", "NotEmpty" -> "es obligatorio";
                    case "Positive" -> "debe ser un número positivo";
                    case "Email" -> "debe ser un correo electrónico válido";
                    case "Pattern" -> "no cumple con el formato requerido";
                    default -> error.getDefaultMessage();
                };
            }

            errors.put(field, "El " + fieldName + " " + message);
        });

        String summary = errors.values().iterator().next();
        return buildResponse(HttpStatus.BAD_REQUEST, summary, errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();
        String message = (rootCause != null) ? rootCause.getMessage() : "";

        if (message != null) {
            Matcher matcher = DUP_KEY_PATTERN.matcher(message);
            if (matcher.find()) {
                String columna = matcher.group(1);
                String valor = matcher.group(2);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiError(columna, "El valor '" + valor + "' para " + columna + " ya está en uso."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("database", "Error de integridad en los datos"));
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<ApiError> handleEntityInUseException(EntityInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("error", ex.getMessage()));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            errors.put(field, field + " " + violation.getMessage());
        });
        System.out.println("ERROR DE VALIDACIÓN: " + ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Error de validación en parámetros", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error capturado: " + ex.getClass().getName() + " - " + ex.getMessage());
    }
}