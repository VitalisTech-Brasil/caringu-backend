package tech.vitalis.caringu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApiExceptions {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String mensagem) {
            super(mensagem);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String mensagem) {
            super(mensagem);
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String mensagem) {
            super(mensagem);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class ConflictException extends RuntimeException {
        public ConflictException(String mensagem) {
            super(mensagem);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String mensagem) {
            super(mensagem);
        }
    }
}
