package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.entity.SessaoTreino;
import tech.vitalis.caringu.enums.SessaoTreino.StatusSessaoTreinoEnum;
import tech.vitalis.caringu.exception.PlanoContratado.PlanoContratadoNaoEncontradoException;
import tech.vitalis.caringu.exception.SessaoTreino.SessaoTreinoNaoEncontradoException;
import tech.vitalis.caringu.repository.SessaoTreinoRepository;
import tech.vitalis.caringu.strategy.SessaoTreino.StatusSessaoTreinoValidationStrategy;

import java.util.Map;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class SessaoTreinoService {

    private final SessaoTreinoRepository sessaoTreinoRepository;

    public SessaoTreinoService(SessaoTreinoRepository sessaoTreinoRepository) {
        this.sessaoTreinoRepository = sessaoTreinoRepository;
    }

    public void atualizarStatus(Integer idSessaoTreino, StatusSessaoTreinoEnum novoStatus) {

        SessaoTreino sessaoTreino = new SessaoTreino();

        try {
            sessaoTreino = sessaoTreinoRepository.findById(idSessaoTreino)
                    .orElseThrow(() -> new SessaoTreinoNaoEncontradoException("Sessão treino com id %d não encontrado.".formatted(idSessaoTreino)));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        validarEnums(Map.of(
                new StatusSessaoTreinoValidationStrategy(), novoStatus
        ));

        sessaoTreino.setStatus(novoStatus);
        sessaoTreinoRepository.save(sessaoTreino);
    }
}
