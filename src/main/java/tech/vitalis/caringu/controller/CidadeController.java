package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Bairro.BairroResponseGetDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeRequestPostDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeRequestPutDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Cidade;
import tech.vitalis.caringu.mapper.CidadeMapper;
import tech.vitalis.caringu.service.CidadeService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService service;
    private final CidadeMapper mapper;

    public CidadeController(CidadeService service, CidadeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CidadeResponseGetDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<CidadeResponseGetDTO>> buscarPorNome(@RequestParam String valor) {

        List<CidadeResponseGetDTO> cidadesPorNome = service.buscarPorNome(valor);

        if (cidadesPorNome.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cidadesPorNome);
    }

    @PostMapping
    public ResponseEntity<CidadeResponseGetDTO> cadastrar(@Valid @RequestBody CidadeRequestPostDTO dto) {
        Cidade cidade = mapper.toEntity(dto);
        CidadeResponseGetDTO responseDTO = service.cadastrar(cidade);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeResponseGetDTO> atualizar(@PathVariable Integer id,
                                                          @Valid @RequestBody CidadeRequestPutDTO dto) {
        Cidade cidadeAtualizada = mapper.toEntity(dto);
        CidadeResponseGetDTO atualizado = service.atualizar(id, cidadeAtualizada);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}