package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.PlanoPertoFimResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.exception.PlanoContratado.PlanoContratadoNaoEncontradoException;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.strategy.PlanoContratado.StatusEnumValidationStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class PlanoContratadoService {

    private final PlanoContratadoRepository planoContratadoRepository;

    public PlanoContratadoService(PlanoContratadoRepository planoContratadoRepository) {
        this.planoContratadoRepository = planoContratadoRepository;
    }

    public List<PlanoContratadoPendenteRequestDTO> listarSolicitacoesPendentes(Integer personalId) {
        return planoContratadoRepository.listarSolicitacoesPendentes(personalId);
    }

    public void atualizarStatus(Integer id, StatusEnum novoStatus) {
        PlanoContratado planoContratado = planoContratadoRepository.findById(id)
                .orElseThrow(() -> new PlanoContratadoNaoEncontradoException("Plano contratado com id %d não encontrado.".formatted(id)));

        validarEnums(Map.of(
                new StatusEnumValidationStrategy(), novoStatus
        ));

        boolean isAvulso = planoContratado.getPlano().getPeriodo().equals(PeriodoEnum.AVULSO);
        boolean isMensal = planoContratado.getPlano().getPeriodo().equals(PeriodoEnum.MENSAL);
        boolean isSemestral = planoContratado.getPlano().getPeriodo().equals(PeriodoEnum.SEMESTRAL);

        if (novoStatus.equals(StatusEnum.ATIVO)) {
            if (!isAvulso) {
                planoContratado.setDataContratacao(LocalDate.now());

                if (isMensal) {
                    planoContratado.setDataFim(LocalDate.now().plusMonths(1));

                } else if (isSemestral) {
                    planoContratado.setDataFim(LocalDate.now().plusMonths(6));
                }
            }
        }

        planoContratado.setStatus(novoStatus);
        planoContratadoRepository.save(planoContratado);
    }

    public Boolean verificarContratacaoPendentePorAluno(Integer alunosId) {
        return planoContratadoRepository.existsByAlunoIdAndStatus(alunosId, StatusEnum.PENDENTE);
    }
}
