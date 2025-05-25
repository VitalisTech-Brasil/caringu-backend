package tech.vitalis.caringu.service;

import org.springframework.stereotype.Component;

@Component
public class NotificacaoEnviarService {

    public void enviarNotificacao(Integer personalTrainerId, String mensagem){
        System.out.println("Enviando notificação para PersonalTrainer ID " + personalTrainerId);
        System.out.println(mensagem);
    }
}
