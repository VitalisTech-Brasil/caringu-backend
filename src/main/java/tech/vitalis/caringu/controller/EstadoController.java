//package tech.vitalis.caringu.controller;
//
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import tech.vitalis.caringu.dtos.Estado.EstadoRequestPostDTO;
//import tech.vitalis.caringu.dtos.Estado.EstadoResponseGetDTO;
//import tech.vitalis.caringu.entity.Estado;
//import tech.vitalis.caringu.mapper.EstadoMapper;
//import tech.vitalis.caringu.service.EstadoService;
//
//import java.util.List;
//
//@RestController
//@SecurityRequirement(name = "Bearer")
//@RequestMapping("/estados")
//public class EstadoController {
//
//    private final EstadoService service;
//    private final EstadoMapper mapper;
//
//    public EstadoController(EstadoService service, EstadoMapper mapper) {
//        this.service = service;
//        this.mapper = mapper;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<EstadoResponseGetDTO>> listar() {
//        List<EstadoResponseGetDTO> estados = service.listar();
//        return ResponseEntity.ok(estados);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<EstadoResponseGetDTO> buscarPorId(@PathVariable Integer id) {
//        return ResponseEntity.ok(service.buscarPorId(id));
//    }
//
//    @GetMapping("/nome")
//    public ResponseEntity<List<EstadoResponseGetDTO>> buscarPorNome(@RequestParam String valor) {
//        List<EstadoResponseGetDTO> resultados = service.buscarPorNome(valor);
//        return ResponseEntity.ok(resultados);
//    }
//
//    @PostMapping
//    public ResponseEntity<EstadoResponseGetDTO> cadastrar(@Valid @RequestBody EstadoRequestPostDTO dto) {
//        Estado estado = mapper.toEntity(dto);
//        EstadoResponseGetDTO responseDTO = service.cadastrar(estado);
//
//        return ResponseEntity.status(201).body(responseDTO);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<EstadoResponseGetDTO> atualizar(@PathVariable Integer id,
//                                                          @Valid @RequestBody EstadoRequestPostDTO dto) {
//        Estado estadoAtualizado = mapper.toEntity(dto);
//        EstadoResponseGetDTO atualizado = service.atualizar(id, estadoAtualizado);
//        return ResponseEntity.ok(atualizado);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
//        service.deletar(id);
//        return ResponseEntity.noContent().build();
//    }
//}
