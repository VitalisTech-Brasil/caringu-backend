package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.EvolucaoCorporal;
import tech.vitalis.caringu.enums.EvolucaoCorporal.TipoEvolucaoEnum;

import java.util.List;

@Repository
public interface EvolucaoCorporalRepository extends JpaRepository<EvolucaoCorporal, Integer> {

    List<EvolucaoCorporal> findByAlunoId(Integer alunoId);
    boolean existsByAlunoIdAndTipoAndPeriodoAvaliacaoAndIdNot(Integer alunoId, TipoEvolucaoEnum tipo, Integer periodoAvaliacao, Integer id);
    boolean existsByAlunoIdAndTipoAndPeriodoAvaliacao(Integer alunoId, TipoEvolucaoEnum tipo, Integer periodoAvaliacao);

}
