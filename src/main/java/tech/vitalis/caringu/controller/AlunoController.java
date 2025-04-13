package tech.vitalis.caringu.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aluno.AlunoCadastroDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoRespostaDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.service.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunoRespostaDTO>> listar() {
        List<AlunoRespostaDTO> listaAlunos = service.listar();

        return ResponseEntity.status(200).body(listaAlunos);
    }

    @PostMapping
    public ResponseEntity<AlunoRespostaDTO> cadastrar(@Valid @RequestBody AlunoCadastroDTO cadastroDTO) {
        Aluno aluno = AlunoMapper.toEntity(cadastroDTO);
        AlunoRespostaDTO respostaDTO = service.cadastrar(aluno);

        return ResponseEntity.status(201).body(respostaDTO);
    }
}
