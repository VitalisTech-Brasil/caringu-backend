package tech.vitalis.caringu.core.exceptions;

public class ApiExceptions {

    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String mensagem) {
            super(mensagem);
        }
    }

    public static class ConflictException extends RuntimeException {
        public ConflictException(String mensagem) {
            super(mensagem);
        }
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String mensagem) {
            super(mensagem);
        }
    }
}
