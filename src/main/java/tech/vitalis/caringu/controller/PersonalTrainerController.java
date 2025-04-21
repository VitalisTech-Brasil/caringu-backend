package tech.vitalis.caringu.controller;

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
    public ResponseEntity<List<PersonalTrainerResponseGetDTO>> listar() {
        List<PersonalTrainerResponseGetDTO> listaPersonalTrainers = service.listar();

        return ResponseEntity.status(200).body(listaPersonalTrainers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalTrainerResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        PersonalTrainerResponseGetDTO personalTrainer = service.buscarPorId(id);
        return ResponseEntity.ok(personalTrainer);
    }

    @PostMapping
    public ResponseEntity<PersonalTrainerResponseGetDTO> cadastrar(@Valid @RequestBody PersonalTrainerRequestPostDTO requestDTO) {
        PersonalTrainer personalTrainer = mapper.toEntity(requestDTO);
        PersonalTrainerResponseGetDTO responseDTO = service.cadastrar(personalTrainer);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalTrainerResponseGetDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PersonalTrainerRequestPostDTO dto) {

        PersonalTrainer novoPersonalTrainer = mapper.toEntity(dto);
        PersonalTrainerResponseGetDTO atualizado = service.atualizar(id, novoPersonalTrainer);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonalTrainerResponsePatchDTO> atualizarParcial(
            @PathVariable Integer id,
            @Valid @RequestBody PersonalTrainerRequestPatchDTO atualizacoes) {

        PersonalTrainerResponsePatchDTO atualizado = service.atualizarParcial(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
