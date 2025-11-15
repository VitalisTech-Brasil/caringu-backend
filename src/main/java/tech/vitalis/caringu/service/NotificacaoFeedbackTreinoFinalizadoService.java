package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.core.exceptions.ApiExceptions;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificacaoFeedbackTreinoFinalizadoService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");


    private final NotificacoesRepository notificacoesRepository;
    private final AulaRepository aulaRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    public NotificacaoFeedbackTreinoFinalizadoService(NotificacoesRepository notificacoesRepository, AulaRepository aulaRepository, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository) {
        this.notificacoesRepository = notificacoesRepository;
        this.aulaRepository = aulaRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
    }

    @Transactional
    public void notificarTreinoFinalizado(Integer aulaId){
        Aula aula = buscarAula(aulaId);

        notificarPersonal(aula);
        notificarAluno(aula);
    }

    @Transactional
    public void notificarPersonal(Aula aula){
        PersonalTrainer personal = aula.getPlanoContratado().getPlano().getPersonalTrainer();
        Integer personalId = personal.getId();

        boolean deveNotificar = preferenciaNotificacaoRepository
                .existsByPessoaIdAndTipoAndAtivadaTrue(
                        personalId,
                        TipoPreferenciaEnum.FEEDBACK_TREINO
                );

        if (!deveNotificar) {
            return;
        }

        Aluno aluno = aula.getPlanoContratado().getAluno();
        String nomeAluno = aluno.getNome();
        String dataHora = aula.getDataHorarioFim() != null
                ? aula.getDataHorarioFim().format(FORMATTER)
                : "data nao definida";

        String titulo = String.format(
                "Treino com %s finalizado em %s. Deixe seu feedback!",
                nomeAluno,
                dataHora
        );

        Notificacoes notificacao = new Notificacoes(
                null,
                personal,
                TipoNotificacaoEnum.FEEDBACK_TREINO,
                titulo,
                false,
                LocalDateTime.now()
        );

        notificacoesRepository.save(notificacao);
    }

    @Transactional
    public void notificarAluno(Aula aula){
        Aluno aluno = aula.getPlanoContratado().getAluno();
        Integer alunoId = aluno.getId();

        boolean deveNotificar = preferenciaNotificacaoRepository
                .existsByPessoaIdAndTipoAndAtivadaTrue(
                        alunoId,
                        TipoPreferenciaEnum.FEEDBACK_TREINO
                );

        if (!deveNotificar) {
            return;
        }

        PersonalTrainer personal = aula.getPlanoContratado().getPlano().getPersonalTrainer();
        String nomePersonal = personal.getNome();
        String dataHora = aula.getDataHorarioFim() != null
                ? aula.getDataHorarioFim().format(FORMATTER)
                : "data nao definida";

        String titulo = String.format(
                "Treino com %s finalizado em %s. Como foi? Deixe seu feedback!",
                nomePersonal,
                dataHora
        );

        Notificacoes notificacao = new Notificacoes(
                null,
                aluno,
                TipoNotificacaoEnum.FEEDBACK_TREINO,
                titulo,
                false,
                LocalDateTime.now()
        );

        notificacoesRepository.save(notificacao);
    }

    private Aula buscarAula(Integer aulaId){
        return aulaRepository.findById(aulaId)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException(
                            "Aula com ID " + aulaId + " não encontrada"
                ));
    }

}
