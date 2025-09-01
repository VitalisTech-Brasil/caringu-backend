package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO;
import tech.vitalis.caringu.entity.SessaoTreino;

import java.util.List;

@Repository
public interface SessaoTreinoRepository extends JpaRepository<SessaoTreino, Integer> {

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioInicio, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.alunoTreino.alunos.id = :alunoId
      AND FUNCTION('YEARWEEK', st.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosInicioSemana(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioFim, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.alunoTreino.alunos.id = :alunoId
      AND FUNCTION('YEARWEEK', st.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosFimSemana(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioInicio, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.alunoTreino.alunos.id = :alunoId
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosInicioTotal(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioFim, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.alunoTreino.alunos.id = :alunoId
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosFimTotal(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO(
                    a.id,
                    pa.nome,
                    pa.urlFotoPerfil,
                    st.id,
                    st.dataHorarioInicio,
                    st.dataHorarioFim,
                    st.status
                )
                FROM PlanoContratado pc
                JOIN Plano pl
                    ON pc.plano.id = pl.id
                JOIN PersonalTrainer pt
                    ON pl.personalTrainer.id = pt.id
                JOIN Pessoa pp
                    ON pt.id = pp.id
                JOIN Aluno a
                    ON pc.aluno.id = a.id
                JOIN Pessoa pa
                    ON a.id = pa.id
                JOIN AlunoTreino at
                    ON a.id = at.alunos.id
                JOIN SessaoTreino st
                    ON at.id = st.alunoTreino.id
                WHERE pc.status = 'ATIVO'
                  AND NOW() BETWEEN pc.dataContratacao AND pc.dataFim
                  AND pt.id = :idPersonal
            """)
    List<SessaoAulasAgendadasResponseDTO> findAllAulasPorPersonal(@Param("idPersonal") Integer idPersonal);
}
