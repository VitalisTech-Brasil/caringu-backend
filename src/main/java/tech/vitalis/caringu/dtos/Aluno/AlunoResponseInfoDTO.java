package tech.vitalis.caringu.dtos.Aluno;

import java.time.LocalDate;
import java.util.List;

public record AlunoResponseInfoDTO(
        String nomeAluno,
        String celular,
        String urlFotoPerfil,

        String nomePersonal,
        String statusPlano,
        LocalDate dataVencimentoPlano,

        Long idAnamnese,
        String objetivoTreino,
        Boolean lesao,
        String lesaoDescricao,
        Integer frequenciaTreino,
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

        Long idAlunosTreinos,
        Long treinosSemana,
        List<LocalDate> datasTreinosSemana
) {
}
