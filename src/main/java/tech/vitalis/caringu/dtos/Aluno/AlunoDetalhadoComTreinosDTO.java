package tech.vitalis.caringu.dtos.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;
import tech.vitalis.caringu.enums.PeriodoEnum;

import java.time.LocalDate;
import java.util.List;

public record AlunoDetalhadoComTreinosDTO(
        Integer idAluno,
        String nomeAluno,
        String celular,
        String urlFotoPerfil,
        NivelExperienciaEnum nivelExperiencia,

        String nomePlano,
        PeriodoEnum periodoPlano,
        Integer totalAulasContratadas,
        LocalDate dataVencimentoPlano,

        Integer idAlunoTreino,
        Long treinosSemana,

        List<String> datasTreinosSemana,

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

        String nomePersonal
) {
}
