package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.PlanoContratado.AtualizarStatusPlanoDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPagamentoPendenteResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.service.PlanoContratadoService;
import tech.vitalis.caringu.service.PlanoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/planos-contratados")
@SecurityRequirement(name = "Bearer")
public class PlanoContratadoController {
    private final PlanoService planoService;
    private final PlanoContratadoService planoContratadoService;

    public PlanoContratadoController(PlanoService planoService, PlanoContratadoService planoContratadoService) {
        this.planoService = planoService;
        this.planoContratadoService = planoContratadoService;
    }

    @GetMapping("/kpis/alunos-ativos/{personalId}")
    public Integer contarAlunosComPlanosAtivos(@PathVariable Integer personalId) {
        return planoService.contarAlunosAtivos(personalId);
    }

    @GetMapping("/qtd-planos-vencendo/{personalId}")
    public Integer buscarQtdPlanosVencendo(@PathVariable Integer personalId) {
        LocalDate limite = LocalDate.now().plusWeeks(2);
        return planoContratadoService.buscarQtdPlanosVencendo(limite, personalId);
    }

    @GetMapping("/solicitacoes-pendentes/{personalId}")
    public ResponseEntity<List<PlanoContratadoPendenteRequestDTO>> listarSolicitacoesPendentes(@PathVariable Integer personalId) {
        List<PlanoContratadoPendenteRequestDTO> pendenteRequestDTO = planoContratadoService.listarSolicitacoesPendentes(personalId);

        if (pendenteRequestDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pendenteRequestDTO);
    }

    @GetMapping("/alunos/{alunosId}/contratacao-pendente")
    public ResponseEntity<List<PlanoContratadoPagamentoPendenteResponseDTO>> verificarContratacaoPendentePorAluno(@PathVariable Integer alunosId) {
        List<PlanoContratadoPagamentoPendenteResponseDTO> contratacaoPendente = planoContratadoService.verificarContratacaoPendentePorAluno(alunosId);

        if (contratacaoPendente.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contratacaoPendente);
    }

    @PostMapping("/contratarPlano/{alunoId}/{planoId}")
    @Operation(summary = "Contratar plano")
    public ResponseEntity<PlanoContratadoRespostaRecord> contratarPlano (@PathVariable Integer alunoId, @PathVariable Integer planoId) {
        PlanoContratadoRespostaRecord planoContratadoRespostaRecord = planoService.contratarPlano(alunoId, planoId);
        return ResponseEntity.status(201).body(planoContratadoRespostaRecord);
    }

    @PatchMapping("/{idPlanoContratado}/status")
    @Operation(summary = "Atualizar status do plano contratado")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Integer idPlanoContratado,
            @RequestBody AtualizarStatusPlanoDTO dto
    ) {
        planoContratadoService.atualizarStatus(idPlanoContratado, dto.status());
        return ResponseEntity.noContent().build();
    }
}
