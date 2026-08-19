package css.gabriel.wallet.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      HttpStatus.NOT_FOUND.getReasonPhrase(),
      ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      HttpStatus.BAD_REQUEST.getReasonPhrase(),
      ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(fieldError ->
      errors.put(fieldError.getField(), fieldError.getDefaultMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    ErrorResponse error = new ErrorResponse(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
      "Erro inesperado no servidor"
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}