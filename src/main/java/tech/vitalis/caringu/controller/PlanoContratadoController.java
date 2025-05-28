package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aluno.PlanoPertoFimResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.AtualizarStatusPlanoDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.service.PlanoContratadoService;
import tech.vitalis.caringu.service.PlanoService;

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

    @GetMapping("/solicitacoes-pendentes/{personalId}")
    public ResponseEntity<List<PlanoContratadoPendenteRequestDTO>> listarSolicitacoesPendentes(@PathVariable Integer personalId) {
        List<PlanoContratadoPendenteRequestDTO> pendenteRequestDTO = planoContratadoService.listarSolicitacoesPendentes(personalId);

        if (pendenteRequestDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pendenteRequestDTO);
    }

    @PostMapping("/contratarPlano/{alunoId}/{planoId}")
    @Operation(summary = "Contratar plano")
    public ResponseEntity<PlanoContratadoRespostaRecord> contratarPlano (@PathVariable Integer alunoId, @PathVariable Integer planoId) {
        PlanoContratadoRespostaRecord planoContratadoRespostaRecord = planoService.contratarPlano(alunoId, planoId);
        return ResponseEntity.status(201).body(planoContratadoRespostaRecord);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Integer id,
            @RequestBody AtualizarStatusPlanoDTO dto
    ) {
        planoContratadoService.atualizarStatus(id, dto.status());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alunos/{alunosId}/contratacao-pendente")
    public ResponseEntity<Boolean> verificarContratacaoPendentePorAluno(@PathVariable Integer alunosId) {
        boolean existeContratacaoPendente = planoContratadoService.verificarContratacaoPendentePorAluno(alunosId);

        return ResponseEntity.ok(existeContratacaoPendente);
    }
}
