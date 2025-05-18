package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.service.PlanoService;

@RestController
@RequestMapping("/planos-contratados")
@SecurityRequirement(name = "Bearer")
public class PlanoContratadoController {
    private final PlanoService planoService;

    public PlanoContratadoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping("/kpis/alunos-ativos/{personalId}")
    public Integer contarAlunosComPlanosAtivos(@PathVariable Integer personalId) {
        return planoService.contarAlunosAtivos(personalId);
    }

    @PostMapping("/contratarPlano/{alunoId}/{planoId}")
    @Operation(summary = "Contratar plano")
    public ResponseEntity<PlanoContratadoRespostaRecord> contratarPlano (@PathVariable Integer alunoId, @PathVariable Integer planoId) {
        PlanoContratadoRespostaRecord planoContratadoRespostaRecord = planoService.contratarPlano(alunoId, planoId);
        return ResponseEntity.status(201).body(planoContratadoRespostaRecord);
    }
}
