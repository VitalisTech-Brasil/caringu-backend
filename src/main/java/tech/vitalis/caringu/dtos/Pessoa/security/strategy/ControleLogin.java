package tech.vitalis.caringu.dtos.Pessoa.security.strategy;

public interface ControleLogin {
    boolean validarBloqueio(String email);
    long tempoRestante(String email);
    boolean registrarFalha(String email);
    void registrarSucesso(String email);
}