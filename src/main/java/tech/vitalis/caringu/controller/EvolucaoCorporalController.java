package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalRequestPostDTO;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalResponseGetDTO;
import tech.vitalis.caringu.service.EvolucaoCorporalService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/evolucao-corporal")
public class EvolucaoCorporalController {

    private final EvolucaoCorporalService service;

    public EvolucaoCorporalController(EvolucaoCorporalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EvolucaoCorporalResponseGetDTO>> listar() {
        List<EvolucaoCorporalResponseGetDTO> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<EvolucaoCorporalResponseGetDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<EvolucaoCorporalResponseGetDTO> lista = service.listarPorAluno(alunoId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<EvolucaoCorporalResponseGetDTO> cadastrar(@Valid @RequestBody EvolucaoCorporalRequestPostDTO dto) {
        EvolucaoCorporalResponseGetDTO response = service.cadastrar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}