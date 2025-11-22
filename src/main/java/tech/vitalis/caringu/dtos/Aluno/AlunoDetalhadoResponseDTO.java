package tech.vitalis.caringu.dtos.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.core.domain.valueObject.FrequenciaTreinoEnum;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

public record AlunoDetalhadoResponseDTO(
        Integer idCorporal,
        Integer idAluno,
        Double peso,
        Double altura,
        String nomeAluno,
        String email,
        String celular,
        String urlFotoPerfil,
        NivelExperienciaEnum nivelExperiencia,
        NivelAtividadeEnum nivelAtividade,

        String nomePlano,
        PeriodoEnum periodoPlano,
        Integer totalAulasContratadas,
        LocalDate dataVencimentoPlano,

        Integer idPlanoContratado,
        Integer idAula,
        Long treinosSemana,
        Long treinosTotal,

        Integer idAnamnese,
        String objetivoTreino,
        Boolean lesao,
        String lesaoDescricao,
        FrequenciaTreinoEnum frequenciaTreino,
        Boolean experiencia,
        String experienciaDescricao,
        Boolean desconforto,
        String desconfortoDescricao,
        Boolean fumante,
        Boolean proteses,
        String protesesDescricao,
        Boolean doencaMetabolica,
        String doencaMetabolicaDescricao,
        Boolean deficiencia,
        String deficienciaDescricao,
        GeneroEnum genero,
        LocalDate dataNascimento
) {
}
