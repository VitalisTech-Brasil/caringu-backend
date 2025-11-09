package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.ExecucaoExercicio.AtualizarStatusExecucaoExercicioRequestDTO;
import tech.vitalis.caringu.service.ExecucaoExercicioService;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/execucoes-exercicios")
public class ExecucaoExercicioController {

    public final ExecucaoExercicioService execucaoExercicioService;

    public ExecucaoExercicioController(ExecucaoExercicioService execucaoExercicioService) {
        this.execucaoExercicioService = execucaoExercicioService;
    }

    @PatchMapping("/{idExecucaoExercicio}/status")
    @Operation(
            summary = "Atualizar o status de execução de um exercício",
            description = "Atualiza parcialmente o campo de status de uma execução de exercício identificada pelo ID informado. " +
                    "Pode ser usado, por exemplo, para marcar o exercício como finalizado."
    )
    public ResponseEntity<Void> atualizarStatusExecucaoExercicio(
            @PathVariable Integer idExecucaoExercicio,
            @Valid @RequestBody AtualizarStatusExecucaoExercicioRequestDTO payload
    ) {
        execucaoExercicioService.atualizarStatusExecucao(idExecucaoExercicio, payload);
        return ResponseEntity.noContent().build();
    }
}
