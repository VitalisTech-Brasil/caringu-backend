package tech.vitalis.caringu.service.EsqueciSenha;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaCodigoDto;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaEmailDto;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaNovaSenhaDto;
import tech.vitalis.caringu.entity.EsqueciSenha;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.repository.EsqueciSenhaRepository;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class EsqueciSenhaService {

    private PessoaRepository pessoaRepository;

    private PasswordEncoder passwordEncoder;

    private EsqueciSenhaRepository esqueciSenhaRepository;


    private JavaMailSender mailSender;

    public EsqueciSenhaService(PessoaRepository pessoaRepository, JavaMailSender mailSender, EsqueciSenhaRepository esqueciSenhaRepository, PasswordEncoder passwordEncoder) {
        this.esqueciSenhaRepository = esqueciSenhaRepository;
        this.passwordEncoder = passwordEncoder;
        this.pessoaRepository = pessoaRepository;
        this.mailSender = mailSender;
    }

    public String validarEmail(EsqueciSenhaEmailDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            return "E-mail inválido.";
        }

        Boolean existe = pessoaRepository.existsByEmail(dto.getEmail());

        if (existe) {
            // 1. Gera o token
            String token = gerarToken4Digitos();

            // 2. Busca a entidade EsqueciSenha para o e-mail informado
            Optional<EsqueciSenha> esqueciSenhaOpt = esqueciSenhaRepository.findByEmail(dto.getEmail());
            EsqueciSenha esqueciSenha;

            // Exemplo: token válido por 15 min
            if (esqueciSenhaOpt.isPresent()) {
                esqueciSenha = esqueciSenhaOpt.get();
            } else {
                // Se não encontrar, cria um novo objeto EsqueciSenha
                esqueciSenha = new EsqueciSenha();
                esqueciSenha.setEmail(dto.getEmail());
            }
            esqueciSenha.setToken(token);
            esqueciSenha.setDataExpiracao(LocalDateTime.now().plusMinutes(15)); // Exemplo: token válido por 15 min
            esqueciSenhaRepository.save(esqueciSenha); // Salva as alterações no banco

            // 3. Envia o e-mail com o token
            enviarEmail(esqueciSenha.getEmail(), token);

            return "E-mail enviado com sucesso.";
        } else {
            return "E-mail não encontrado.";
        }
    }

    private String gerarToken4Digitos() {
        int token = (int) (Math.random() * 9000) + 1000; // Gera um número entre 1000 e 9999
        return String.valueOf(token);
    }

    private void enviarEmail(String email, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Código de Recuperação de Senha");
            message.setText("Seu código de recuperação é: " + token + ". Ele expira em 10 minutos.");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }

    public Boolean vaidarToken(EsqueciSenhaCodigoDto dto) {
        Optional<EsqueciSenha> esqueciSenhaOpt = esqueciSenhaRepository.findByEmailAndToken(dto.getEmail(), dto.getCodigo());

        if (!esqueciSenhaOpt.isPresent()) {
            return false; // Token ou e-mail não encontrado
        }

        EsqueciSenha esqueciSenha = esqueciSenhaOpt.get();

        // Verifica se o token já expirou
        if (esqueciSenha.getDataExpiracao().isBefore(LocalDateTime.now())) {
            return false; // Token expirado
        }

        return true; // Token válido
    }

    public Boolean cadastrarNovaSenha(EsqueciSenhaNovaSenhaDto dto) {

        Optional<Pessoa> optionalPessoa = pessoaRepository.findByEmail(dto.email());

        if (optionalPessoa.isPresent()) {
            Pessoa pessoa = optionalPessoa.get();

            String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";

            if (!Pattern.matches(regex, dto.novaSenha())) {
                throw new SenhaInvalidaException("A senha deve incluir pelo menos uma letra maiúscula, um número e um caractere especial.");
            }

            // 3. Criptografa a nova senha fornecida
            String senhaCriptografada = passwordEncoder.encode(dto.novaSenha());

            // 4. Define a nova senha criptografada no objeto Pessoa
            pessoa.setSenha(senhaCriptografada);

            // 5. Salva a pessoa com a nova senha
            pessoaRepository.save(pessoa);

            // 6. Retorna true indicando sucesso
            return true;
        } else {
            // Se o e-mail não foi encontrado no banco
            return false;
        }
    }

}