package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.PlanoPertoFimResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.exception.PlanoContratado.PlanoContratadoNaoEncontradoException;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.strategy.PlanoContratado.StatusEnumValidationStrategy;

import java.util.List;
import java.util.Map;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class PlanoContratadoService {

    private final PlanoContratadoRepository planoContratadoRepository;

    public PlanoContratadoService(PlanoContratadoRepository planoContratadoRepository) {
        this.planoContratadoRepository = planoContratadoRepository;
    }

    public void atualizarStatus(Integer id, StatusEnum novoStatus) {
        PlanoContratado plano = planoContratadoRepository.findById(id)
                .orElseThrow(() -> new PlanoContratadoNaoEncontradoException("Plano contratado com id %d não encontrado.".formatted(id)));

        validarEnums(Map.of(
                new StatusEnumValidationStrategy(), novoStatus
        ));

        plano.setStatus(novoStatus);
        planoContratadoRepository.save(plano);
    }
}
