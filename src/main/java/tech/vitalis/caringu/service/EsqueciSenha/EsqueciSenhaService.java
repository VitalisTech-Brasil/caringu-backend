package tech.vitalis.caringu.service.EsqueciSenha;

import jakarta.validation.Valid;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaEmailDto;
import tech.vitalis.caringu.entity.EsqueciSenha;
import tech.vitalis.caringu.repository.EsqueciSenhaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class EsqueciSenhaService {

    private EsqueciSenhaRepository esqueciSenhaRepository;
    private JavaMailSender mailSender;

    public EsqueciSenhaService(EsqueciSenhaRepository esqueciSenhaRepository, JavaMailSender mailSender) {
        this.esqueciSenhaRepository = esqueciSenhaRepository;
        this.mailSender = mailSender;
    }

    public String validarEmail(EsqueciSenhaEmailDto dto) {
        Optional<EsqueciSenha> existe =  esqueciSenhaRepository.findByEmail(dto.getEmail());

        if (existe.isPresent()) {
            String token = gerarToken4Digitos();
            salvo informações no banco

            envio email
        }

        não exite o email no banco
    }

    private String gerarToken4Digitos() {
        int token = (int)(Math.random() * 9000) + 1000; // Gera um número entre 1000 e 9999
        return String.valueOf(token);
    }

    private void enviarEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Recuperação de Senha");
        message.setText("Seu código de recuperação é: " + token + ". Ele expira em 10 minutos.");
        mailSender.send(message);
    }


//    public String enviarToken(@Valid EsqueciSenhaEmailDto dto) {
//        // 1. Gerar token de 4 dígitos
//        String token = String.format("%04d", new Random().nextInt(10000));
//
//        // 2. Criar entidade e salvar no banco
//        EsqueciSenha esqueciSenha = new EsqueciSenha();
//        esqueciSenha.setEmail(dto.getEmail());
//        esqueciSenha.setToken(token);
//        esqueciSenha.setExpirationTime(LocalDateTime.now().plusMinutes(10)); // expira em 10 minutos
//
//        esqueciSenhaRepository.save(esqueciSenha);
//
//        // 3. Enviar email
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(dto.getEmail());
//        message.setSubject("Recuperação de senha - CaringU");
//        message.setText("Seu código de recuperação é: " + token);
//
//        mailSender.send(message);
//
//        // 4. Retornar sucesso
//        return "Token enviado com sucesso para o email: " + dto.getEmail();
//    }
}
