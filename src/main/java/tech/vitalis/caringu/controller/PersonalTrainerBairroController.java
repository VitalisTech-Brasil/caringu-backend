package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerBairroRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerBairroResponseGetDTO;
import tech.vitalis.caringu.service.PersonalTrainerBairroService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/associacoes")
public class PersonalTrainerBairroController {

    private final PersonalTrainerBairroService service;

    public PersonalTrainerBairroController(PersonalTrainerBairroService service) {
        this.service = service;
    }

    @GetMapping("/por-personal/{personalId}")
    public ResponseEntity<List<PersonalTrainerBairroResponseGetDTO>> listarPorPersonal(@PathVariable Integer personalId) {
        return ResponseEntity.ok(service.buscarPorPersonalTrainerId(personalId));
    }

    @GetMapping("/por-bairro/{bairroId}")
    public ResponseEntity<List<PersonalTrainerBairroResponseGetDTO>> listarPorBairro(@PathVariable Integer bairroId) {
        return ResponseEntity.ok(service.buscarPorBairroId(bairroId));
    }

    @PostMapping
    public ResponseEntity<PersonalTrainerBairroResponseGetDTO> associar(
            @RequestBody @Valid PersonalTrainerBairroRequestPostDTO dto
    ) {
        PersonalTrainerBairroResponseGetDTO response = service.associar(dto);
        return ResponseEntity.status(201).body(response);
    }
}