package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.SessaoTreino.*;
import tech.vitalis.caringu.entity.SessaoTreino;
import tech.vitalis.caringu.enums.SessaoTreino.StatusSessaoTreinoEnum;
import tech.vitalis.caringu.exception.SessaoTreino.SessaoTreinoNaoEncontradoException;
import tech.vitalis.caringu.repository.SessaoTreinoRepository;
import tech.vitalis.caringu.strategy.SessaoTreino.StatusSessaoTreinoValidationStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class SessaoTreinoService {

    private final SessaoTreinoRepository sessaoTreinoRepository;

    public SessaoTreinoService(SessaoTreinoRepository sessaoTreinoRepository) {
        this.sessaoTreinoRepository = sessaoTreinoRepository;
    }

    public List<SessaoAulasAgendadasResponseDTO> listarAulasPorPersonal(Integer idPersonal) {
        return sessaoTreinoRepository.findAllAulasPorPersonal(idPersonal);
    }

    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(Integer idAluno, Integer idExercicio) {
        return sessaoTreinoRepository.buscarEvolucaoCarga(idExercicio, idAluno);
    }

    public List<EvolucaoTreinoCumpridoResponseDTO> buscarEvolucaoTreinosCumpridosMensal(Integer alunoId, Integer exercicioId) {
        return sessaoTreinoRepository.buscarEvolucaoTreinosCumpridosMensal(alunoId, exercicioId);
    }

    public HorasTreinadasResponseDTO buscarHorasTreinadas(Integer idAluno, Integer idExercicio) {
        List<Object[]> resultados = sessaoTreinoRepository.buscarHorasAgrupadasPorAlunoExercicio(idAluno, idExercicio);

        List<HorasTreinadasSemanaMesDTO> dados = resultados.stream()
                .map(r -> new HorasTreinadasSemanaMesDTO(
                        (Integer) r[0],
                        (String) r[1],
                        (Integer) r[2],
                        (String) r[3],
                        ((Number) r[4]).intValue(),
                        ((Number) r[5]).intValue(),
                        ((Number) r[6]).intValue(),
                        ((Number) r[7]).doubleValue()
                ))
                .collect(Collectors.toList());

        return new HorasTreinadasResponseDTO(idAluno, idExercicio, dados);
    }

    public void atualizarStatus(Integer idSessaoTreino, StatusSessaoTreinoEnum novoStatus) {

        SessaoTreino sessaoTreino = sessaoTreinoRepository.findById(idSessaoTreino)
                .orElseThrow(() -> new SessaoTreinoNaoEncontradoException("Sessão treino com id %d não encontrado.".formatted(idSessaoTreino)));

        validarEnums(Map.of(
                new StatusSessaoTreinoValidationStrategy(), novoStatus
        ));

        if (novoStatus.equals(StatusSessaoTreinoEnum.REALIZADO)) {
            sessaoTreino.setDataHorarioFim(LocalDateTime.now());
        }

        if (novoStatus.equals(StatusSessaoTreinoEnum.AGENDADO)) {
            sessaoTreino.setDataHorarioFim(null);
        }

        sessaoTreino.setStatus(novoStatus);
        sessaoTreinoRepository.save(sessaoTreino);
    }
}
