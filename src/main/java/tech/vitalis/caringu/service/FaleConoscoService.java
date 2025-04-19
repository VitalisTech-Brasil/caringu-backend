package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.FaleConosco.FaleConoscoDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class FaleConoscoService {

    private final JavaMailSender mailSender;

    private String emailEquipe;

    public FaleConoscoService(JavaMailSender mailSender, @Value("${spring.mail.username}") String emailEquipe) {
        this.mailSender = mailSender;
        this.emailEquipe = emailEquipe;
    }

    @Async
    public void enviarEmailAsync(String destinatario, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);
        mensagem.setFrom(emailEquipe);
        mailSender.send(mensagem);
    }

    public void processarMensagem(FaleConoscoDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String horarioEnvio = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(formatter);

        // 1. Envia email de confirmação para o usuário
        enviarEmailAsync(dto.email(), "CaringU | Fale Conosco",
                "Olá " + dto.nome() + ",\n\nRecebemos sua mensagem com o assunto: \n\"" + dto.mensagem() +
                        "\".\n\nEm breve responderemos. Obrigado! 😎" +
                        "\n\nHorário de envio: " + horarioEnvio);

        // 2. Envia mensagem do usuário para o Gmail da equipe
        String conteudo = "Nova mensagem recebida no Fale Conosco:\n\n" +
                "Nome: " + dto.nome() + "\n" +
                "Email: " + dto.email() + "\n" +
                "Telefone: " + dto.telefone() + "\n\n" +
                "Mensagem:\n" + dto.mensagem() + "\n\n" +
                "Horário de envio: " + horarioEnvio;

        enviarEmailAsync(emailEquipe, "Fale Conosco - Nova mensagem de " + dto.nome(), conteudo);
    }
}
