package tech.vitalis.caringu.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.Anamnese.AnamneseJaCadastradaException;
import tech.vitalis.caringu.exception.Anamnese.AnamneseNaoEncontradaException;
import tech.vitalis.caringu.exception.ApiExceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import tech.vitalis.caringu.exception.Bairro.BairroJaExisteException;
import tech.vitalis.caringu.exception.Bairro.BairroNaoEncontradoException;
import tech.vitalis.caringu.exception.Cidade.CidadeJaExisteException;
import tech.vitalis.caringu.exception.Cidade.CidadeNaoEncontradaException;
import tech.vitalis.caringu.exception.Especialidade.EspecialidadeNaoEncontrada;
import tech.vitalis.caringu.exception.Estado.EstadoJaExisteException;
import tech.vitalis.caringu.exception.Estado.EstadoNaoEncontradoException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalJaExisteException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalNaoEncontradaException;
import tech.vitalis.caringu.exception.PersonalTrainer.CrefJaExisteException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.PessoaNaoEncontradaException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.exception.PreferenciasNotificacao.PreferenciasNotificacaoJaExisteException;
import tech.vitalis.caringu.exception.PreferenciasNotificacao.PreferenciasNotificacaoNaoEncontradaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiExceptions.ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(ApiExceptions.ResourceNotFoundException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleAlunoNaoEncontrado(AlunoNaoEncontradoException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(EspecialidadeNaoEncontrada.class)
    public ResponseEntity<Map<String, Object>> handleAlunoNaoEncontrado(EspecialidadeNaoEncontrada ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(PersonalNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handlePersonalNaoEncontrado(PersonalNaoEncontradoException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handlePessoaNaoEncontrado(PessoaNaoEncontradaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(AnamneseNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleAnamneseNaoEncontrado(AnamneseNaoEncontradaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(BairroNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleBairroNaoEncontrado(BairroNaoEncontradoException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(CidadeNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleCidadeNaoEncontrada(CidadeNaoEncontradaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(EstadoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleEstadoNaoEncontrado(EstadoNaoEncontradoException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(PreferenciasNotificacaoNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handlePreferenciasNotificacaoNaoEncontrada(PreferenciasNotificacaoNaoEncontradaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(EvolucaoCorporalNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleEvolucaoCorporalNaoEncontrada(EvolucaoCorporalNaoEncontradaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(EvolucaoCorporalJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleEvolucaoCorporalJaExiste(EvolucaoCorporalJaExisteException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(PreferenciasNotificacaoJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handlePreferenciasNotificacaolJaExiste(PreferenciasNotificacaoJaExisteException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(BairroJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleBairroJaExiste(BairroJaExisteException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(CidadeJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleCidadeJaExiste(CidadeJaExisteException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(EstadoJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleEstadoJaExiste(EstadoJaExisteException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(AnamneseJaCadastradaException.class)
    public ResponseEntity<Map<String, Object>> handleAnamneseJaCadastrada(AnamneseJaCadastradaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(CrefJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleCrefJaExistente(CrefJaExisteException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> handleEmailDuplicado(EmailJaCadastradoException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleSenhaInvalida(SenhaInvalidaException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleEnumInvalido(HttpMessageNotReadableException exception, WebRequest request) {
        Throwable causa = exception.getCause();
        String mensagemErro = "Requisição malformada.";

        if (causa instanceof InvalidFormatException ife) {
            if (ife.getTargetType().equals(LocalDate.class)) {
                mensagemErro = "O formato da data está incorreto. Use o formato 'yyyy-MM-dd'.";
            } else if (ife.getTargetType().isEnum()) {
                mensagemErro = "Valor inválido fornecido para o campo do tipo enum. Valores possíveis: " +
                        Arrays.toString(ife.getTargetType().getEnumConstants());
            }
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", mensagemErro, request);
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String error, String message, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        response.put("path", request.getDescription(false).replace("uri=", ""));
        logger.warn("Erro {} - {}: {}", status.value(), error, message);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Dados inválidos");

        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", errorMessage, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Dados inválidos");

        return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", errorMessage, request);
    }

}
