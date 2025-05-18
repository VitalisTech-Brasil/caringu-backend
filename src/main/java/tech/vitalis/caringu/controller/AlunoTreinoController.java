package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoRequestUpdateDTO;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoResponseGetDTO;
import tech.vitalis.caringu.dtos.KPIs.KpiContadorResponse;
import tech.vitalis.caringu.service.AlunoTreinoService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/alunos-treinos")
public class AlunoTreinoController {

    private final AlunoTreinoService service;

    public AlunoTreinoController(AlunoTreinoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os Alunos Treinos")
    public ResponseEntity<List<AlunoTreinoResponseGetDTO>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar por Id os Alunos Treinos")
    public ResponseEntity<AlunoTreinoResponseGetDTO> listarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/kpis/proximos-vencimento/{personalId}")
    public Integer contarProximosDoVencimento(@PathVariable Integer personalId,
                                              @RequestParam(defaultValue = "7") int dias) {
        return service.contarTreinosProximosVencimento(personalId, dias);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo Aluno Treino")
    public ResponseEntity<AlunoTreinoResponseGetDTO> cadastrar(@Valid @RequestBody AlunoTreinoRequestPostDTO alunoTreinoDTO){
        AlunoTreinoResponseGetDTO alunoTreinoCriado = service.cadastrar(alunoTreinoDTO);
        return ResponseEntity.status(201).body(alunoTreinoCriado);
    }

    @PutMapping("/{id}/aluno/{alunoId}/treinoExercicio/{treinoExercicioId}")
    @Operation(summary = "Atualizar o Aluno Treino")
    public ResponseEntity<AlunoTreinoResponseGetDTO> atualizar(@PathVariable Integer id, @PathVariable Integer alunoId, @PathVariable Integer treinoExercicioId, @Valid @RequestBody AlunoTreinoRequestUpdateDTO alunoTreinoDTO){
        return ResponseEntity.ok(service.atualizar(id, alunoTreinoDTO, alunoId, treinoExercicioId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover Aluno Treino")
    public ResponseEntity<Void> removerAlunoTreino(@PathVariable Integer id){
        service.removerAssociacao(id);
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
