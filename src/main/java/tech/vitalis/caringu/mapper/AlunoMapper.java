package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Aluno.*;
import tech.vitalis.caringu.dtos.PerfilAluno.AlunoGetPerfilDetalhesDTO;
import tech.vitalis.caringu.dtos.PerfilAluno.PessoaGetPerfilDetalhesDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.repository.TreinoFinalizadoRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AlunoMapper {

    private final TreinoFinalizadoRepository treinoFinalizadoRepository;

    public AlunoMapper(TreinoFinalizadoRepository treinoFinalizadoRepository) {
        this.treinoFinalizadoRepository = treinoFinalizadoRepository;
    }

    public Aluno toEntity(AlunoRequestPostDTO cadastroDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(cadastroDTO.nome());
        aluno.setEmail(cadastroDTO.email());
        aluno.setSenha(cadastroDTO.senha());
        aluno.setCelular(cadastroDTO.celular());
        aluno.setUrlFotoPerfil(cadastroDTO.urlFotoPerfil());
        aluno.setDataNascimento(cadastroDTO.dataNascimento());
        aluno.setGenero(cadastroDTO.genero());

        aluno.setPeso(cadastroDTO.peso());
        aluno.setAltura(cadastroDTO.altura());
        aluno.setNivelAtividade(cadastroDTO.nivelAtividade());
        aluno.setNivelExperiencia(cadastroDTO.nivelExperiencia());

        return aluno;
    }

    public List<AlunoDetalhadoComTreinosDTO> preencherDatasTreinos(List<AlunoDetalhadoResponseDTO> dados) {
        Map<Integer, List<AlunoDetalhadoResponseDTO>> agrupadoPorAluno = dados.stream()
                .collect(Collectors.groupingBy(AlunoDetalhadoResponseDTO::idAluno));

        return agrupadoPorAluno.entrySet().stream()
                .map(entry -> {
                    Integer idAluno = entry.getKey();
                    List<AlunoDetalhadoResponseDTO> lista = entry.getValue();

                    AlunoDetalhadoResponseDTO dtoBase = lista.getFirst();

                    Long totalTreinosSemana = lista.stream()
                            .map(AlunoDetalhadoResponseDTO::treinosSemana)
                            .filter(Objects::nonNull)
                            .reduce(0L, Long::sum);

                    List<String> datas = treinoFinalizadoRepository.buscarDatasTreinosSemana(idAluno);

                    return new AlunoDetalhadoComTreinosDTO(
                            dtoBase.idAluno(), dtoBase.nomeAluno(), dtoBase.celular(), dtoBase.urlFotoPerfil(), dtoBase.nivelExperiencia(),
                            dtoBase.nomePlano(), dtoBase.periodoPlano(), dtoBase.totalAulasContratadas(), dtoBase.dataVencimentoPlano(),
                            dtoBase.idAlunoTreino(), totalTreinosSemana, datas,
                            dtoBase.idAnamnese(), dtoBase.objetivoTreino(), dtoBase.lesao(), dtoBase.lesaoDescricao(), dtoBase.frequenciaTreino(),
                            dtoBase.experiencia(), dtoBase.experienciaDescricao(), dtoBase.desconforto(), dtoBase.desconfortoDescricao(),
                            dtoBase.fumante(), dtoBase.proteses(), dtoBase.protesesDescricao(),
                            dtoBase.doencaMetabolica(), dtoBase.doencaMetabolicaDescricao(),
                            dtoBase.deficiencia(), dtoBase.deficienciaDescricao(),
                            dtoBase.nomePersonal()
                    );
                }).toList();
    }

    public AlunoResponseGetDTO toResponseDTO(Aluno aluno) {

        return new AlunoResponseGetDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCelular(),
                aluno.getUrlFotoPerfil(),
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
