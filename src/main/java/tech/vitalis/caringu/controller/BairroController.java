package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Bairro.BairroRequestPostDTO;
import tech.vitalis.caringu.dtos.Bairro.BairroRequestPutDTO;
import tech.vitalis.caringu.dtos.Bairro.BairroResponseGetDTO;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.mapper.BairroMapper;
import tech.vitalis.caringu.service.BairroService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/bairros")
public class BairroController {

    private final BairroService service;
    private final BairroMapper mapper;

    public BairroController(BairroService service, BairroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BairroResponseGetDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BairroResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<BairroResponseGetDTO>> buscarPorNome(@RequestParam String valor) {
        List<BairroResponseGetDTO> bairrosPorNome = service.buscarPorNome(valor);

        if (bairrosPorNome.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bairrosPorNome);
    }

    @PostMapping
    public ResponseEntity<BairroResponseGetDTO> cadastrar(@Valid @RequestBody BairroRequestPostDTO dto) {
        Bairro bairro = mapper.toEntity(dto);
        BairroResponseGetDTO responseDTO = service.cadastrar(bairro);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BairroResponseGetDTO> atualizar(@PathVariable Integer id,
                                                          @Valid @RequestBody BairroRequestPutDTO dto) {
        Bairro bairroAtualizado = mapper.toEntity(dto);
        BairroResponseGetDTO atualizado = service.atualizar(id, bairroAtualizado);

        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
