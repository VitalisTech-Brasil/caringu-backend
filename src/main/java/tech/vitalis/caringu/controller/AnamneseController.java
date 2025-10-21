package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseRequestPatchDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseRequestPostDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseResponseGetDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseResponsePatchDTO;
import tech.vitalis.caringu.dtos.PerfilAluno.AnamneseGetPerfilDetalhesDTO;
import tech.vitalis.caringu.service.AnamneseService;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/anamnese")
public class AnamneseController {

    private final AnamneseService service;
    public AnamneseController(AnamneseService service) {
        this.service = service;
    }

    @GetMapping("/{alunoId}")
    @Operation(summary = "Recuperar detalhes completos do aluno, incluindo informações de pessoa, anamnese e dados físicos")
    public ResponseEntity<AnamneseGetPerfilDetalhesDTO> obterDetalhesAluno(@PathVariable Integer alunoId) {
        AnamneseGetPerfilDetalhesDTO detalhes = service.obterDetalhes(alunoId);
        return ResponseEntity.ok(detalhes);
    }

    @GetMapping("/kpis/pendentes/{idPersonal}")
    public Integer contarPendentes(@PathVariable Integer idPersonal) {
        return service.contarAnamnesesPendentes(idPersonal);
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar anamnese",
            description = "Cria uma nova anamnese no sistema com as informações fornecidas no corpo da requisição. Requer autenticação via JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Anamnese cadastrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AnamneseResponseGetDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição malformada, dados de Anamnese inválidos"
                    )
            }
    )
    public ResponseEntity<AnamneseResponseGetDTO> criar(@Valid @RequestBody AnamneseRequestPostDTO requestDTO) {

        AnamneseResponseGetDTO responseDTO = service.cadastrar(requestDTO);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @PatchMapping("/{anamneseId}")
    public ResponseEntity<AnamneseResponsePatchDTO> atualizarParcialmente(
            @PathVariable Integer anamneseId,
            @RequestBody @Valid AnamneseRequestPatchDTO dto
    ) {
        AnamneseResponsePatchDTO responsePatchDTO = service.atualizarParcialmente(anamneseId, dto);
        return ResponseEntity.ok().body(responsePatchDTO);
    }
}
