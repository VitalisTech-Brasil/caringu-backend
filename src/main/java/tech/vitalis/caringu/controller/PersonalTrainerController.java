package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    public PersonalTrainerController(PersonalTrainerService service) {
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar personal trainers")
    public ResponseEntity<List<PersonalTrainerResponseGetDTO>> listar() {
        List<PersonalTrainerResponseGetDTO> listaPersonalTrainers = service.listar();

        return ResponseEntity.status(200).body(listaPersonalTrainers);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar personal trainer por ID")
    public ResponseEntity<PersonalTrainerResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        PersonalTrainerResponseGetDTO personalTrainer = service.buscarPorId(id);
        return ResponseEntity.ok(personalTrainer);
    }

    @PostMapping
    @Operation(summary = "Cadastrar personal trainer")
    public ResponseEntity<PersonalTrainerResponseGetDTO> cadastrar(@Valid @RequestBody PersonalTrainerRequestPostDTO requestDTO) {
        PersonalTrainer personalTrainer = PersonalTrainerMapper.toEntity(requestDTO);
        PersonalTrainerResponseGetDTO responseDTO = service.cadastrar(personalTrainer);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar personal trainer")
    public ResponseEntity<PersonalTrainerResponseGetDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PersonalTrainerRequestPostDTO dto) {

        PersonalTrainer novoPersonalTrainer = PersonalTrainerMapper.toEntity(dto);
        PersonalTrainerResponseGetDTO atualizado = service.atualizar(id, novoPersonalTrainer);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar personal trainer parcialmente")
    public ResponseEntity<PersonalTrainerResponsePatchDTO> atualizarParcial(
            @PathVariable Integer id,
            @Valid @RequestBody PersonalTrainerRequestPatchDTO atualizacoes) {

        PersonalTrainerResponsePatchDTO atualizado = service.atualizarParcial(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover personal trainer")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
