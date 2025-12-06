package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AcompanhamentoAulaCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoModeloCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.ExercicioResumoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AcompanhamentoAulaResponseDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AulaTreinoExercicioRemarcarAulaResponseDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.ExercicioAcompanhamentoAulaDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AulaTreinoExercicioMapper {

    private final ArmazenamentoService armazenamentoInterface;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    public AulaTreinoExercicioMapper(ArmazenamentoService armazenamentoInterface) {
        this.armazenamentoInterface = armazenamentoInterface;
    }

    // Converte TreinoExercicio → AulaTreinoExercicio vinculado à Aula
    public AulaTreinoExercicio toEntity(Aula aula, TreinoExercicio treinoExercicio, Integer ordem) {
        AulaTreinoExercicio entity = new AulaTreinoExercicio();
        entity.setAula(aula);
        entity.setTreinoExercicio(treinoExercicio);
        entity.setOrdem(ordem != null ? ordem : treinoExercicio.getId()); // ou usar posição sequencial
        entity.setCarga(treinoExercicio.getCarga());
        entity.setRepeticoes(treinoExercicio.getRepeticoes());
        entity.setSeries(treinoExercicio.getSeries());
        entity.setDescanso(treinoExercicio.getDescanso());
        entity.setObservacoesPersonalizadas(treinoExercicio.getObservacoesPersonalizadas());
        return entity;
    }

    // Converte AulaTreinoExercicio → DTO de resposta
    public AulaTreinoExercicioRemarcarAulaResponseDTO toResponse(AulaTreinoExercicio entity) {
        return new AulaTreinoExercicioRemarcarAulaResponseDTO(
                entity.getId(),
                entity.getTreinoExercicio().getId(),
                entity.getTreinoExercicio().getExercicio().getId(),
                entity.getTreinoExercicio().getExercicio().getNome(),
                entity.getTreinoExercicio().getExercicio().getGrupoMuscular(),
                entity.getOrdem(),
                entity.getCarga(),
                entity.getRepeticoes(),
                entity.getSeries(),
                entity.getDescanso(),
                entity.getObservacoesPersonalizadas()
        );
    }

    public List<AulaComTreinoDTO> toAulaComTreinoDTO(List<AulaComTreinoModeloCruDTO> listaCrua) {
        Map<Integer, AulaComTreinoDTO> aulasAgrupadas = new LinkedHashMap<>();

        for (AulaComTreinoModeloCruDTO dto : listaCrua) {

            String urlFotoTemporaria = dto.urlFotoPerfil() != null
                    ? armazenamentoInterface.gerarUrlPreAssinada(dto.urlFotoPerfil(), Duration.ofMinutes(5))
                    : null;

            String diaSemanaCapitalizado = dto.dataHorarioInicio()
                    .getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, LOCALE_PT_BR);
            diaSemanaCapitalizado = diaSemanaCapitalizado.substring(0, 1).toUpperCase() + diaSemanaCapitalizado.substring(1);

            final String diaSemana = diaSemanaCapitalizado;

            aulasAgrupadas.computeIfAbsent(dto.idAula(), id -> new AulaComTreinoDTO(
                    dto.idAluno(),
                    dto.nomeAluno(),
                    dto.idAula(),
                    dto.dataHorarioInicio().format(FORMATO_DATA),
                    diaSemana,
                    String.format("%s - %s",
                            dto.dataHorarioInicio().format(FORMATO_HORA),
                            dto.dataHorarioFim().format(FORMATO_HORA)
                    ),
                    dto.treinoId(),
                    dto.nomeTreino(),
                    new ArrayList<>(),
                    dto.nomePersonal(),
                    urlFotoTemporaria
            )).exercicios().add(new ExercicioResumoDTO(
                    dto.exercicioId(),
                    dto.nomeExercicio()
            ));
        }

        return new ArrayList<>(aulasAgrupadas.values());
    }

    public AcompanhamentoAulaResponseDTO toAcompanhamentoAulaResponseDTO(List<AcompanhamentoAulaCruDTO> linhas) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        }

        AcompanhamentoAulaCruDTO primeira = linhas.getFirst();

        List<ExercicioAcompanhamentoAulaDTO> exercicios = linhas.stream()
                .map(this::toExercicioAcompanhamentoAulaDTO)
                .collect(Collectors.toList());

        return new AcompanhamentoAulaResponseDTO(
                primeira.idAluno(),
                primeira.idAula(),
                primeira.aulaStatus(),
                primeira.dataInicioAula(),
                primeira.dataFimAula(),
                primeira.idTreino(),
                primeira.nomeTreino(),
                exercicios
        );
    }

    private ExercicioAcompanhamentoAulaDTO toExercicioAcompanhamentoAulaDTO(AcompanhamentoAulaCruDTO l) {
        return new ExercicioAcompanhamentoAulaDTO(
                l.idExecucaoExercicio(),
                l.nomeExercicio(),
                l.cargaExecutada(),
                String.format("%dx%d", l.seriesExecutadas(), l.repeticoesExecutadas()),
                l.descansoExecutado(),
                l.idAulaTreinoExercicio(),
                l.observacoesPersonalizadas(),
                l.urlVideo(),
                l.grupoMuscular().getValue(),
                l.finalizado()
        );
    }
}
