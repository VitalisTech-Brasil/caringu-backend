package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.PersonalTrainerEspecialidade.EspecialidadeAssociacaoRequest;
import tech.vitalis.caringu.service.PersonalTrainerEspecialidadeService;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/personal-trainers-especialidades")
public class PersonalTrainerEspecialidadeController {

    private final PersonalTrainerEspecialidadeService service;

    public PersonalTrainerEspecialidadeController(PersonalTrainerEspecialidadeService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> associarEspecialidades(
            @PathVariable Integer id,
            @RequestBody EspecialidadeAssociacaoRequest request
    ) {
        service.associarEspecialidades(id, request.especialidades());
        return ResponseEntity.ok().build();
    }
}
