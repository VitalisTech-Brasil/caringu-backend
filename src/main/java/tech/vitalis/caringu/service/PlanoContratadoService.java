package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.PlanoPertoFimResponseDTO;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;

import java.util.List;

@Service
public class PlanoContratadoService {

    private final PlanoContratadoRepository planoContratadoRepository;

    public PlanoContratadoService(PlanoContratadoRepository planoContratadoRepository) {
        this.planoContratadoRepository = planoContratadoRepository;
    }

    public List<PlanoPertoFimResponseDTO> listarAlunosComPlanoPertoDoFim(Integer personalId) {
        return planoContratadoRepository.buscarAlunosComPlanoPertoDoFim(personalId);
    }
}
