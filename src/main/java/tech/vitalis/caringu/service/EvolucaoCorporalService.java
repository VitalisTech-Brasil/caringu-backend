package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalRequestPostDTO;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.EvolucaoCorporal;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalJaExisteException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalNaoEncontradaException;
import tech.vitalis.caringu.mapper.EvolucaoCorporalMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.EvolucaoCorporalRepository;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvolucaoCorporalService {

    private final EvolucaoCorporalRepository repository;
    private final AlunoRepository alunoRepository;
    private final EvolucaoCorporalMapper mapper;
    private final ArmazenamentoService armazenamentoService;

    public EvolucaoCorporalService(EvolucaoCorporalRepository repository,
                                   AlunoRepository alunoRepository,
                                   EvolucaoCorporalMapper mapper, ArmazenamentoService armazenamentoService) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.mapper = mapper;
        this.armazenamentoService = armazenamentoService;
    }

    public List<EvolucaoCorporalResponseGetDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public List<EvolucaoCorporalResponseGetDTO> listarPorAluno(Integer alunoId) {
        alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID %d não encontrado".formatted(alunoId)));

        return repository.findByAlunoId(alunoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public EvolucaoCorporalResponseGetDTO cadastrar(EvolucaoCorporalRequestPostDTO dto, MultipartFile arquivo) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new AlunoNaoEncontradoException(
                        "Aluno com ID %d não encontrado".formatted(dto.alunoId()))
                );

        Optional<EvolucaoCorporal> fotoAntiga = repository
                .findByAlunoId(aluno.getId()).stream()
                .filter(e -> e.getTipo().equals(dto.tipo()))
                .filter(e -> e.getUrlFotoShape() != null)
                .findFirst();

        if (fotoAntiga.isPresent()){
            try {
                armazenamentoService.deletarArquivoPorUrl(
                        fotoAntiga.get().getUrlFotoShape()
                );
            }catch (Exception e){
                System.err.println("Erro ao deletar foto antiga: " + e.getMessage());
            }
        }

        String nomeArquivo = null;
        String urlCompleta = null;

        if (arquivo != null && !arquivo.isEmpty()){
            urlCompleta = armazenamentoService.uploadArquivo(arquivo);
            nomeArquivo = extrairNomeArquivo(urlCompleta);
        }

        EvolucaoCorporal nova = mapper.toEntity(dto);
        nova.setAluno(aluno);
        nova.setUrlFotoShape(urlCompleta);
        nova.setDataEnvio(LocalDateTime.now());

        EvolucaoCorporal salvo = repository.save(nova);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Integer id) {
        boolean existe = repository.existsById(id);

        if (!existe) {
            throw new EvolucaoCorporalNaoEncontradaException("Evolução corporal com ID %d não encontrada.".formatted(id));
        }

        repository.deleteById(id);
    }

    private String extrairNomeArquivo(String url) {
        if (url == null || !url.startsWith("http")) {
            return url;
        }
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
