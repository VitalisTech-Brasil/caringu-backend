package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Aluno.*;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AlunoMapper {

    private final AulaRepository aulaRepository;
    private final ArmazenamentoService armazenamentoInterface;

    public AlunoMapper(AulaRepository aulaRepository, ArmazenamentoService armazenamentoInterface) {
        this.aulaRepository = aulaRepository;
        this.armazenamentoInterface = armazenamentoInterface;
    }

    public Aluno toEntity(AlunoRequestPostDTO cadastroDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(cadastroDTO.nome());
        aluno.setEmail(cadastroDTO.email());
        aluno.setSenha(cadastroDTO.senha());
        aluno.setCelular(cadastroDTO.celular());
        // Armazena apenas o fileKey no banco
        aluno.setUrlFotoPerfil(cadastroDTO.urlFotoPerfil());
        aluno.setDataNascimento(cadastroDTO.dataNascimento());
        aluno.setGenero(cadastroDTO.genero());
        aluno.setPeso(cadastroDTO.peso());
        aluno.setAltura(cadastroDTO.altura());
        aluno.setNivelAtividade(cadastroDTO.nivelAtividade());
        aluno.setNivelExperiencia(cadastroDTO.nivelExperiencia());

        return aluno;
    }

    public List<AlunoDetalhadoComTreinosDTO> consolidarPorAluno(List<AlunoDetalhadoResponseDTO> lista) {
        // Primeiro converte ResponseDTO para o DTO com treinos
        List<AlunoDetalhadoComTreinosDTO> convertidos = lista.stream()
                .map(this::mapParaDTOComTreinosBase)
                .toList();

        return convertidos.stream()
                .collect(Collectors.groupingBy(AlunoDetalhadoComTreinosDTO::idAluno))
                .values().stream()
                .map(this::consolidar)
                .toList();
    }

    public AlunoDetalhadoComTreinosDTO toAlunoDetalhadoComTreinosDTO(AlunoDetalhadoResponseDTO dto) {
        return mapParaDTOComTreinosBase(dto);
    }

    private AlunoDetalhadoComTreinosDTO mapParaDTOComTreinosBase(AlunoDetalhadoResponseDTO dto) {
        String urlFotoTemporaria = dto.urlFotoPerfil() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(dto.urlFotoPerfil(), Duration.ofMinutes(5))
                : null;

        return new AlunoDetalhadoComTreinosDTO(
                dto.idAluno(),
                dto.nomeAluno(),
                dto.email(),
                dto.celular(),
                urlFotoTemporaria,
                dto.peso(),
                dto.altura(),
                dto.nivelAtividade(),
                dto.nivelExperiencia(),
                dto.nomePlano(),
                dto.periodoPlano(),
                dto.totalAulasContratadas(),
                dto.dataVencimentoPlano(),
                dto.idPlanoContratado(),
                dto.idAula(),
                dto.treinosSemana(),
                dto.treinosTotal(),
                null, null, null, null,
                dto.idAnamnese(),
                dto.objetivoTreino(),
                dto.lesao(),
                dto.lesaoDescricao(),
                dto.frequenciaTreino(),
                dto.experiencia(),
                dto.experienciaDescricao(),
                dto.desconforto(),
                dto.desconfortoDescricao(),
                dto.fumante(),
                dto.proteses(),
                dto.protesesDescricao(),
                dto.doencaMetabolica(),
                dto.doencaMetabolicaDescricao(),
                dto.deficiencia(),
                dto.deficienciaDescricao()
        );
    }

    private AlunoDetalhadoComTreinosDTO consolidar(List<AlunoDetalhadoComTreinosDTO> duplicados) {
        AlunoDetalhadoComTreinosDTO base = duplicados.getFirst();

        List<String> horariosInicioSemana = aulaRepository.buscarHorariosInicioSemana(base.idAluno());
        List<String> horariosFimSemana = aulaRepository.buscarHorariosFimSemana(base.idAluno());
        List<String> horariosInicioTotal = aulaRepository.buscarHorariosInicioTotal(base.idAluno());
        List<String> horariosFimTotal = aulaRepository.buscarHorariosFimTotal(base.idAluno());

        Long treinosSemana = duplicados.stream().mapToLong(AlunoDetalhadoComTreinosDTO::treinosSemana).sum();
        Long treinosTotal = duplicados.stream().mapToLong(AlunoDetalhadoComTreinosDTO::treinosTotal).sum();

        Integer idPlanoContratado = duplicados.stream()
                .map(AlunoDetalhadoComTreinosDTO::idPlanoContratado)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        // Gera URL temporária
        String urlFotoTemporaria = base.urlFotoPerfil() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(base.urlFotoPerfil(), Duration.ofMinutes(5))
                : null;

        return new AlunoDetalhadoComTreinosDTO(
                base.idAluno(),
                base.nomeAluno(),
                base.email(),
                base.celular(),
                urlFotoTemporaria,
                base.peso(),
                base.altura(),
                base.nivelAtividade(),
                base.nivelExperiencia(),
                base.nomePlano(),
                base.periodoPlano(),
                base.totalAulasContratadas(),
                base.dataVencimentoPlano(),
                idPlanoContratado,
                base.idAula(),
                treinosSemana,
                treinosTotal,
                horariosInicioSemana,
                horariosFimSemana,
                horariosInicioTotal,
                horariosFimTotal,
                base.idAnamnese(),
                base.objetivoTreino(),
                base.lesao(),
                base.lesaoDescricao(),
                base.frequenciaTreino(),
                base.experiencia(),
                base.experienciaDescricao(),
                base.desconforto(),
                base.desconfortoDescricao(),
                base.fumante(),
                base.proteses(),
                base.protesesDescricao(),
                base.doencaMetabolica(),
                base.doencaMetabolicaDescricao(),
                base.deficiencia(),
                base.deficienciaDescricao()
        );
    }

    public AlunoResponseGetDTO toResponseDTO(Aluno aluno) {
        // Gera URL temporária aqui, pro front
        String urlFoto = aluno.getUrlFotoPerfil() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(aluno.getUrlFotoPerfil(), Duration.ofMinutes(5))
                : null;

        return new AlunoResponseGetDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCelular(),
                urlFoto,
                aluno.getDataNascimento(),
                aluno.getGenero(),
                aluno.getPeso(),
                aluno.getAltura(),
                aluno.getNivelAtividade(),
                aluno.getNivelExperiencia(),
                aluno.getDataCadastro()
        );
    }

    public AlunoResponsePatchDadosFisicosDTO toResponseDadosFisicosDTO(Aluno aluno) {
        return new AlunoResponsePatchDadosFisicosDTO(
                aluno.getId(),
                aluno.getPeso(),
                aluno.getAltura(),
                aluno.getNivelAtividade(),
                aluno.getNivelExperiencia()
        );
    }

}
