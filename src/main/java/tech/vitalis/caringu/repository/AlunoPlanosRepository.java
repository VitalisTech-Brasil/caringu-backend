package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.PlanoContratado;

import java.util.List;

@Repository
public interface AlunoPlanosRepository extends JpaRepository<PlanoContratado, Integer> {

    @Query(value = """
            SELECT 
                pc.id AS plano_contratado_id,
                pc.status,
                pc.data_fim,
                p.nome AS nome_plano,
                p.quantidade_aulas,
                p.valor_aulas,
                pt.id AS id_personal,
                pt_pessoa.nome AS nome_personal,
                pt_pessoa.email AS email_personal,
                pt.experiencia AS experiencia_personal,
                GROUP_CONCAT(DISTINCT CONCAT(b.nome, ' - ', c.nome) SEPARATOR ', ') AS locais_atendimento,
                av.id AS avaliacao_id,
                av.nota AS avaliacao_nota,
                av.comentario AS avaliacao_comentario,
                av.data_avaliacao AS avaliacao_data
            FROM 
                planos_contratados pc
            INNER JOIN 
                planos p ON pc.planos_id = p.id
            INNER JOIN 
                alunos a ON pc.alunos_id = a.id
            INNER JOIN 
                pessoas pes ON a.id = pes.id
            INNER JOIN 
                personal_trainers pt ON p.personal_trainers_id = pt.id
            INNER JOIN 
                pessoas pt_pessoa ON pt.id = pt_pessoa.id
            LEFT JOIN 
                personal_trainers_bairros ptb ON pt.id = ptb.personal_trainers_id
            LEFT JOIN 
                bairros b ON ptb.bairro_id = b.id
            LEFT JOIN 
                cidades c ON b.cidades_id = c.id
            LEFT JOIN
                avaliacoes_personal_trainers av ON av.personal_trainer_id = pt.id AND av.aluno_id = a.id
            WHERE 
                pc.alunos_id = :alunoId
            GROUP BY 
                pc.id, pc.status, pc.data_fim, p.nome, p.quantidade_aulas,
                p.valor_aulas, pt.id, pt_pessoa.nome, pt_pessoa.email, pt.experiencia,
                av.id, av.nota, av.comentario, av.data_avaliacao
            ORDER BY 
                pc.data_contratacao DESC
            """, nativeQuery = true)
    List<Object[]> findPlanosByAlunoId(@Param("alunoId") Integer alunoId);
}
