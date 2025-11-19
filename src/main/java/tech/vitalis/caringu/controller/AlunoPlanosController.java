package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.AlunoPlanos.AlunoPlanosDTO;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoRequestDTO;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoResponseDTO;
import tech.vitalis.caringu.service.AlunoPlanosService;
import tech.vitalis.caringu.service.AvaliacaoService;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Aluno Planos", description = "Endpoints para consulta de planos contratados por alunos e avaliações")
public class AlunoPlanosController {

    private final AlunoPlanosService service;
    private final AvaliacaoService avaliacaoService;

    public AlunoPlanosController(AlunoPlanosService service, AvaliacaoService avaliacaoService) {
        this.service = service;
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping("/{idAluno}/planos")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Buscar planos de um aluno",
            description = "Retorna todos os planos contratados por um aluno específico, incluindo informações do personal trainer e locais de atendimento"
    )
    public ResponseEntity<List<AlunoPlanosDTO>> buscarPlanosPorAluno(@PathVariable Integer idAluno) {
        List<AlunoPlanosDTO> planos = service.buscarPlanosPorAluno(idAluno);
        return ResponseEntity.ok(planos);
    }

    @PostMapping("/{idAluno}/planos/{idPersonal}/avaliar")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Avaliar Personal Trainer",
            description = "Cria uma nova avaliação ou atualiza uma existente do personal trainer feita pelo aluno. " +
                    "Sempre retorna 200 OK. Para verificar se já existe avaliação, consulte GET /alunos/{idAluno}/planos."
    )
    public ResponseEntity<AvaliacaoResponseDTO> avaliarPersonalTrainer(
            @PathVariable Integer idAluno,
            @PathVariable Integer idPersonal,
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoData) {

        AvaliacaoRequestDTO avaliacaoCompleta = new AvaliacaoRequestDTO(
                idPersonal,
                idAluno,
                avaliacaoData.nota(),
                avaliacaoData.comentario()
        );

        return avaliacaoService.criarOuAtualizarAvaliacao(avaliacaoCompleta);
    }
}
