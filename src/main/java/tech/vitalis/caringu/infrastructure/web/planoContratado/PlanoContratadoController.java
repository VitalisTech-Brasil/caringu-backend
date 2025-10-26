package tech.vitalis.caringu.infrastructure.web.planoContratado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.core.adapter.planoContratado.PlanoContratadoDTOMapper;
import tech.vitalis.caringu.core.application.usecases.planoContratado.PlanoContratadoUseCase;
import tech.vitalis.caringu.core.domain.entity.PlanoContratado;
import tech.vitalis.caringu.dtos.PlanoContratado.AtualizarStatusPlanoDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.service.PlanoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/planos-contratados")
@SecurityRequirement(name = "Bearer")
public class PlanoContratadoController {
    private final PlanoService planoService;
    private final PlanoContratadoDTOMapper mapper;
    private final PlanoContratadoUseCase planoContratadoUseCase;

    public PlanoContratadoController(PlanoService planoService, PlanoContratadoDTOMapper mapper, PlanoContratadoUseCase planoContratadoUseCase) {
        this.planoService = planoService;
        this.mapper = mapper;
        this.planoContratadoUseCase = planoContratadoUseCase;
    }

    @GetMapping("/kpis/alunos-ativos/{personalId}")
    public Integer contarAlunosComPlanosAtivos(@PathVariable Integer personalId) {
        return planoService.contarAlunosAtivos(personalId);
    }

    @GetMapping("/kpis/qtd-planos-vencendo/{personalId}")
    public ResponseEntity<Integer> buscarQtdPlanosVencendo(@PathVariable Integer personalId) {
        LocalDate limite = LocalDate.now().plusWeeks(2);
        Integer quantidade = planoContratadoUseCase.findQtdPlanosVencendoInteractor(limite, personalId);
        return ResponseEntity.ok(quantidade);
    }

    @GetMapping("/solicitacoes-pendentes/{personalId}")
    public ResponseEntity<List<GetPlanoContratadoPendenteRequest>> listarSolicitacoesPendentes(@PathVariable Integer personalId) {
        List<PlanoContratado> planos = planoContratadoUseCase.listSolicitacoesPendentesInteractor(personalId);

        if (planos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<GetPlanoContratadoPendenteRequest> response = planos.stream()
                .map(mapper::toPendenteResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/alunos/{alunosId}/contratacao-pendente")
    public ResponseEntity<List<GetPlanoContratadoPagamentoPendenteResponse>> verificarContratacaoPendentePorAluno(@PathVariable Integer alunosId) {
        List<PlanoContratado> planosContratados =
                planoContratadoUseCase.verificarContratacaoPendentePorAlunoInteractor(alunosId);

        if (planosContratados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<GetPlanoContratadoPagamentoPendenteResponse> response =
                planosContratados.stream()
                        .map(mapper::toPagamentoPendenteResponseDTO)
                        .toList();


        return ResponseEntity.ok(response);
    }

    @PostMapping("/contratarPlano/{alunoId}/{planoId}")
    @Operation(summary = "Contratar plano")
    public ResponseEntity<PlanoContratadoResponse> contratarPlano (@PathVariable Integer alunoId, @PathVariable Integer planoId) {
        PlanoContratadoResponse planoContratadoRespostaRecord = planoService.contratarPlano(alunoId, planoId);
        return ResponseEntity.status(201).body(planoContratadoRespostaRecord);
    }

    @PatchMapping("/{idPlanoContratado}/status")
    @Operation(summary = "Atualizar status do plano contratado")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Integer idPlanoContratado,
            @RequestBody AtualizarStatusPlanoDTO dto
    ) {
        planoContratadoUseCase.updateStatusPlanoInteractor(idPlanoContratado, dto.status());
        return ResponseEntity.noContent().build();
    }
}
