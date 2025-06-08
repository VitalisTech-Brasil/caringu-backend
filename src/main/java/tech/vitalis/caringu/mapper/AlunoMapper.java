package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Aluno.*;
import tech.vitalis.caringu.dtos.PerfilAluno.AlunoGetPerfilDetalhesDTO;
import tech.vitalis.caringu.dtos.PerfilAluno.PessoaGetPerfilDetalhesDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.repository.TreinoFinalizadoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    private AlunoDetalhadoComTreinosDTO mapParaDTOComTreinosBase(AlunoDetalhadoResponseDTO dto) {
        return new AlunoDetalhadoComTreinosDTO(
                dto.idAluno(),
                dto.nomeAluno(),
                dto.email(),
                dto.celular(),
                dto.urlFotoPerfil(),
                dto.peso(),
                dto.altura(),
                dto.nivelAtividade(),
                dto.nivelExperiencia(),
                dto.nomePlano(),
                dto.periodoPlano(),
                dto.totalAulasContratadas(),
                dto.dataVencimentoPlano(),
                dto.idAlunoTreino(),
                dto.treinosSemana(),
                dto.treinosTotal(),
                null, null, null, null, // horários ainda serão adicionados depois
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
        AlunoDetalhadoComTreinosDTO base = duplicados.getFirst(); // os dados são iguais entre os duplicados

        // Horários e contagens agregadas por aluno (não por treino)
        List<String> horariosInicioSemana = treinoFinalizadoRepository.buscarHorariosInicioSemana(base.idAluno());
        List<String> horariosFimSemana = treinoFinalizadoRepository.buscarHorariosFimSemana(base.idAluno());
        List<String> horariosInicioTotal = treinoFinalizadoRepository.buscarHorariosInicioTotal(base.idAluno());
        List<String> horariosFimTotal = treinoFinalizadoRepository.buscarHorariosFimTotal(base.idAluno());

        Long treinosSemana = duplicados.stream().mapToLong(AlunoDetalhadoComTreinosDTO::treinosSemana).sum();
        Long treinosTotal = duplicados.stream().mapToLong(AlunoDetalhadoComTreinosDTO::treinosTotal).sum();

        Integer idAlunoTreino = duplicados.stream()
                .filter(dto -> dto.idAlunoTreino() != null &&
                        (
                                PeriodoEnum.AVULSO.equals(dto.periodoPlano()) ||
                                        (dto.dataVencimentoPlano() != null && !dto.dataVencimentoPlano().isBefore(LocalDate.now()))
                        )
                )
                .map(AlunoDetalhadoComTreinosDTO::idAlunoTreino)
                .findFirst()
                .orElse(null);

        return new AlunoDetalhadoComTreinosDTO(
                base.idAluno(),
                base.nomeAluno(),
                base.email(),
                base.celular(),
                base.urlFotoPerfil(),
                base.peso(),
                base.altura(),
                base.nivelAtividade(),
                base.nivelExperiencia(),
                base.nomePlano(),
                base.periodoPlano(),
                base.totalAulasContratadas(),
                base.dataVencimentoPlano(),
                idAlunoTreino,
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
