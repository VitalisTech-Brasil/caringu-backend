package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPatchDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponsePatchDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.service.PersonalTrainerService;

import java.util.List;

@RestController
@RequestMapping("/personal-trainers")
public class PersonalTrainerController {

    private final PersonalTrainerService service;
    private final PersonalTrainerMapper mapper;

    public PersonalTrainerController(PersonalTrainerService service, PersonalTrainerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Listar personal trainers",
            description = "Retorna uma lista de todos os personal trainers cadastrados no sistema. Requer autenticação via JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de personal trainers retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonalTrainerResponseGetDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado, autenticação necessária"
                    )
            }
    )
    public ResponseEntity<List<PersonalTrainerResponseGetDTO>> listar() {
        List<PersonalTrainerResponseGetDTO> listaPersonalTrainers = service.listar();

        return ResponseEntity.status(200).body(listaPersonalTrainers);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Buscar personal trainer por ID",
            description = "Retorna as informações de um personal trainer específico, identificado pelo ID. Requer autenticação via JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Personal trainer encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonalTrainerResponseGetDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Personal trainer não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado, autenticação necessária"
                    )
            }
    )
    public ResponseEntity<PersonalTrainerResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        PersonalTrainerResponseGetDTO personalTrainer = service.buscarPorId(id);
        return ResponseEntity.ok(personalTrainer);
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar personal trainer",
            description = "Cria um novo personal trainer no sistema com as informações fornecidas no corpo da requisição. Não requer autenticação.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Personal trainer cadastrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonalTrainerResponseGetDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição malformada, dados de personal trainer inválidos"
                    )
            }
    )
    public ResponseEntity<PersonalTrainerResponseGetDTO> cadastrar(@Valid @RequestBody PersonalTrainerRequestPostDTO requestDTO) {
        PersonalTrainer personalTrainer = mapper.toEntity(requestDTO);
        PersonalTrainerResponseGetDTO responseDTO = service.cadastrar(personalTrainer);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/verificacao-cref")
    @Operation(
            summary = "Validar a existência do CREF",
            description = "Verifica se o CREF informado já foi cadastrado no sistema. Retorna um status 409 (Conflict) caso o CREF já exista. Não requer autenticação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CREF não encontrado no sistema. Pode ser cadastrado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "CREF já cadastrado no sistema, conflito de dados"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição malformada, CREF inválido"
                    )
            }
    )
    public ResponseEntity<Boolean> verificarCref(@RequestParam String cref) {
        boolean existe = service.crefExiste(cref);
        if (existe) {
            return ResponseEntity.status(409).body(true);
        } else {
            return ResponseEntity.status(200).body(false);
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Atualizar personal trainer",
            description = "Atualiza as informações de um personal trainer específico, identificado pelo ID. Requer autenticação via JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Personal trainer atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonalTrainerResponseGetDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Personal trainer não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado, autenticação necessária"
                    )
            }
    )
    public ResponseEntity<PersonalTrainerResponseGetDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PersonalTrainerRequestPatchDTO dto) {

        PersonalTrainer novoPersonalTrainer = mapper.toEntity(dto);
        PersonalTrainerResponseGetDTO atualizado = service.atualizar(id, novoPersonalTrainer);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Atualizar personal trainer parcialmente",
            description = "Permite atualizar apenas algumas informações de um personal trainer, identificado pelo ID. Requer autenticação via JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Personal trainer parcialmente atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PersonalTrainerResponsePatchDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Personal trainer não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado, autenticação necessária"
                    )
            }
    )
    public ResponseEntity<PersonalTrainerResponsePatchDTO> atualizarParcial(
            @PathVariable Integer id,
            @Valid @RequestBody PersonalTrainerRequestPatchDTO atualizacoes) {

        PersonalTrainerResponsePatchDTO atualizado = service.atualizarParcial(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Remover personal trainer",
            description = "Remove um personal trainer do sistema, identificado pelo ID. Requer autenticação via JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Personal trainer removido com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Personal trainer não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado, autenticação necessária"
                    )
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/especialidades/{idEspecialidade}")
    public ResponseEntity<Void> removerEspecialidade(@PathVariable Integer id, @PathVariable Integer idEspecialidade) {
        try {
            service.removerEspecialidade(id, idEspecialidade);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
