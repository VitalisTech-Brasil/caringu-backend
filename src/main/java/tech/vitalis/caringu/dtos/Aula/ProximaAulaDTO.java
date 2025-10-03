package tech.vitalis.caringu.dtos.Aula;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDateTime;

public record ProximasAulasDTO(Integer aulaId,
                               Integer planoContratadoId,
                               LocalDateTime dataHorarioInicio,
                               LocalDateTime dataHorarioFim,
                               AulaStatusEnum status
) {}