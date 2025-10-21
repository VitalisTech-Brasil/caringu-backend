package tech.vitalis.caringu.infrastructure.web.anamnese;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.core.adapter.anamnese.AnamneseDTOMapper;
import tech.vitalis.caringu.core.application.usecases.anamnese.*;
import tech.vitalis.caringu.core.domain.entity.Anamnese;
import tech.vitalis.caringu.infrastructure.web.anamnese.AnamneseResponse;
import tech.vitalis.caringu.infrastructure.web.anamnese.CreateAnamneseRequest;
import tech.vitalis.caringu.infrastructure.web.anamnese.PatchAnamneseRequest;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/anamnese")
public class AnamneseController {
    private final AnamneseDTOMapper anamneseDTOMapper;
    private final AnamneseUseCase anamneseUseCase;

    public AnamneseController(AnamneseDTOMapper anamneseDTOMapper, AnamneseUseCase anamneseUseCase) {
        this.anamneseDTOMapper = anamneseDTOMapper;
        this.anamneseUseCase = anamneseUseCase;
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
                                    schema = @Schema(implementation = AnamneseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição malformada, dados de Anamnese inválidos"
                    )
            }
    )
    public ResponseEntity<AnamneseResponse> criar(@Valid @RequestBody CreateAnamneseRequest request) {
        Anamnese userBusinessObj = anamneseDTOMapper.toEntity(request);
        Anamnese anamnese = anamneseUseCase.createAnamnese(userBusinessObj);
        return ResponseEntity.status(200).body(anamneseDTOMapper.toResponse(anamnese));
    }

    @GetMapping("/{alunoId}")
    @Operation(summary = "Recuperar detalhes completos do aluno, incluindo informações de pessoa, anamnese e dados físicos")
    public ResponseEntity<AnamneseResponse> obterDetalhesAluno(@PathVariable Integer alunoId) {
        Anamnese detalhes = anamneseUseCase.getAnamnese(alunoId);
        AnamneseResponse anamneseResponse = anamneseDTOMapper.toResponse(detalhes);
        return ResponseEntity.ok(anamneseResponse);
    }

    @GetMapping("/kpis/pendentes/{idPersonal}")
    public Integer contarPendentes(@PathVariable Integer idPersonal) {
        return anamneseUseCase.countAnamnesesPendentesByPersonal(idPersonal);
    }

    @PatchMapping("/{anamneseId}")
    public ResponseEntity<AnamneseResponse> atualizarParcialmente(
            @PathVariable Integer anamneseId,
            @RequestBody @Valid PatchAnamneseRequest dto
    ) {

        Anamnese anamneseDomain = anamneseDTOMapper.toAnamneseFromPatch(dto);
        Anamnese update = anamneseUseCase.patchAnamnese(anamneseId, anamneseDomain);
        AnamneseResponse response = anamneseDTOMapper.toResponse(update);


        return ResponseEntity.ok().body(response);
    }

}
