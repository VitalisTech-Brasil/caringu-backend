package tech.vitalis.caringu.dtos.PlanoContratado;

import java.time.LocalDate;

/**
 * Projection para mapear resultado da query nativa de planos contratados por aluno.
 * Utilizado pelo PlanoContratadoRepository para retornar dados agregados de múltiplas tabelas.
 */
public interface AlunoPlanoProjection {

    Long getPlanoContratadoId();

    String getStatus();

    LocalDate getDataFim();

    String getNomePlano();

    Integer getQuantidadeAulas();

    Double getValorAulas();

    String getNomePersonal();

    String getEmailPersonal();

    String getExperienciaPersonal();

    String getLocaisAtendimento();
}

