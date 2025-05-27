package tech.vitalis.caringu.dtos.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;

import java.time.LocalDate;
import java.util.List;

public record AlunoDetalhadoDTO(
        Integer idAluno,
        String nomeAluno,
        String celular,
        String urlFotoPerfil,
        NivelExperienciaEnum nivelExperiencia,

        String nomePlano,
        Integer totalAulasContratadas,
        LocalDate dataVencimentoPlano,

        Long treinosSemana,
        Long treinosTotal,

        List<String> horariosInicioSemana,
        List<String> horariosFimSemana,
        List<String> horariosInicioTotal,
        List<String> horariosFimTotal,

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
        String deficienciaDescricao
) {
}
