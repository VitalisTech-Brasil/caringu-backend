package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Aluno.AlunoDetalhadoResponseDTO;
import tech.vitalis.caringu.entity.Aluno;

import java.util.List;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Aluno.AlunoDetalhadoResponseDTO(
        a.id, a.peso, a.altura, a.nome, a.email, a.celular, a.urlFotoPerfil, a.nivelExperiencia, a.nivelAtividade,
        pl.nome, pl.periodo, pl.quantidadeAulas, pc.dataFim,
        at.id,
        COUNT(DISTINCT tfSemana.id),
        COUNT(DISTINCT tfTotal.id),
        ana.id, ana.objetivoTreino, ana.lesao, ana.lesaoDescricao, ana.frequenciaTreino,
        ana.experiencia, ana.experienciaDescricao, ana.desconforto, ana.desconfortoDescricao,
        ana.fumante, ana.proteses, ana.protesesDescricao,
        ana.doencaMetabolica, ana.doencaMetabolicaDescricao,
        ana.deficiencia, ana.deficienciaDescricao
    )
    FROM PlanoContratado pc
    JOIN pc.plano pl
    JOIN pl.personalTrainer pt
    JOIN pc.aluno a
    LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
    LEFT JOIN AlunoTreino at ON at.alunos.id = a.id
    LEFT JOIN TreinoFinalizado tfSemana
        ON tfSemana.alunoTreino.id = at.id
        AND FUNCTION('YEARWEEK', tfSemana.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    LEFT JOIN TreinoFinalizado tfTotal ON tfTotal.alunoTreino.id = at.id
    WHERE pt.id = :personalId
      AND pc.status = 'ATIVO'
      AND pc.dataContratacao = (
        SELECT MAX(p2.dataContratacao)
        FROM PlanoContratado p2
        WHERE p2.aluno.id = pc.aluno.id AND p2.status = 'ATIVO'
    )
    GROUP BY a.id, a.nome, a.celular, a.urlFotoPerfil, a.nivelExperiencia,
             pl.nome, pl.periodo, pl.quantidadeAulas, pc.dataFim,
             at.id, ana.id
""")
    List<AlunoDetalhadoResponseDTO> buscarDetalhesPorPersonal(@Param("personalId") Integer personalId);
}
