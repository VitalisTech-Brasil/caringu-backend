package tech.vitalis.caringu.dtos.PerfilAluno;

public record AlunoResponseGetPerfilDetalhesDTO(
        PessoaGetPerfilDetalhesDTO pessoa,
        AlunoGetPerfilDetalhesDTO aluno,
        AnamneseGetPerfilDetalhesDTO anamnese
) {}
