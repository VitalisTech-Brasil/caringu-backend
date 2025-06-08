//package tech.vitalis.caringu.service;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//import tech.vitalis.caringu.dtos.Estado.EstadoResponseGetDTO;
//import tech.vitalis.caringu.entity.Estado;
//import tech.vitalis.caringu.exception.Estado.EstadoJaExisteException;
//import tech.vitalis.caringu.exception.Estado.EstadoNaoEncontradoException;
//import tech.vitalis.caringu.mapper.EstadoMapper;
//import tech.vitalis.caringu.repository.EstadoRepository;
//
//import java.util.List;
//
//@Service
//public class EstadoService {
//
//    private final EstadoRepository repository;
//    private final EstadoMapper mapper;
//
//    public EstadoService(EstadoRepository repository, EstadoMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//
//    public List<EstadoResponseGetDTO> listar() {
//        return repository.findAll()
//                .stream()
//                .map(mapper::toResponseDTO)
//                .toList();
//    }
//
//    public EstadoResponseGetDTO buscarPorId(Integer id) {
//        Estado estado = repository.findById(id)
//                .orElseThrow(() -> new EstadoNaoEncontradoException("Estado com ID %d não encontrado".formatted(id)));
//
//        return mapper.toResponseDTO(estado);
//    }
//
//    public List<EstadoResponseGetDTO> buscarPorNome(String nome) {
//        return repository.findByNomeContainingIgnoreCase(nome)
//                .stream()
//                .map(mapper::toResponseDTO)
//                .toList();
//    }
//
//    public EstadoResponseGetDTO cadastrar(Estado estado) {
//        boolean jaExiste = repository.existsByNomeIgnoreCase(estado.getNome());
//
//        if (jaExiste) {
//            throw new EstadoJaExisteException("Já existe um estado com o nome '%s'".formatted(estado.getNome()));
//        }
//
//        Estado salvo = repository.save(estado);
//
//        return mapper.toResponseDTO(salvo);
//    }
//
//    public EstadoResponseGetDTO atualizar(Integer id, Estado novoEstado) {
//        Estado existente = repository.findById(id)
//                .orElseThrow(() -> new EstadoNaoEncontradoException("Estado com ID %d não encontrado".formatted(id)));
//
//        existente.setNome(novoEstado.getNome());
//
//        Estado salvo = repository.save(existente);
//        return mapper.toResponseDTO(salvo);
//    }
//
//    public void deletar(Integer id) {
//        boolean existe = repository.existsById(id);
//
//        if (!existe) {
//            throw new EstadoNaoEncontradoException("Estado com ID %d não existe".formatted(id));
//        }
//
//        repository.deleteById(id);
//    }
//}
