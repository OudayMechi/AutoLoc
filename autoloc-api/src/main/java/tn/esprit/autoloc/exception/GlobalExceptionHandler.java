package tn.esprit.autoloc.exception;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
    return error(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
    return error(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new TreeMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(fieldError -> errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));
    return error(HttpStatus.BAD_REQUEST, "Les données envoyées sont invalides", errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors = new TreeMap<>();
    ex.getConstraintViolations().forEach(violation ->
        errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
    return error(HttpStatus.BAD_REQUEST, "Les données envoyées sont invalides", errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleUnreadableRequestBody(HttpMessageNotReadableException ex) {
    return error(HttpStatus.BAD_REQUEST, "Le corps JSON est invalide ou incomplet");
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    return error(HttpStatus.CONFLICT, "L'opération entre en conflit avec une donnée existante");
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
    return error(HttpStatus.METHOD_NOT_ALLOWED, "Cette méthode HTTP n'est pas prise en charge");
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ApiError> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
    return error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Le type de contenu envoyé n'est pas pris en charge");
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiError> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    return error(HttpStatus.BAD_REQUEST, "Un paramètre de la requête est invalide");
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(NoResourceFoundException ex) {
    return error(HttpStatus.NOT_FOUND, "La route demandée est introuvable");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(Exception ex) {
    LOGGER.error("Erreur interne lors du traitement de la requête", ex);
    return error(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue");
  }

  private ResponseEntity<ApiError> error(HttpStatus status, String message) {
    return error(status, message, Map.of());
  }

  private ResponseEntity<ApiError> error(HttpStatus status, String message, Map<String, String> errors) {
    return ResponseEntity.status(status).body(new ApiError(status.value(), message, Instant.now(), errors));
  }

  public record ApiError(int status, String message, Instant horodatage, Map<String, String> erreurs) {
  }
}
