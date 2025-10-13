package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Aluno.*;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.repository.AulaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlunoMapper {

    private final AulaRepository aulaRepository;

    public AlunoMapper(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
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

    public AlunoDetalhadoComTreinosDTO toAlunoDetalhadoComTreinosDTO(AlunoDetalhadoResponseDTO dto) {
        return mapParaDTOComTreinosBase(dto);
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
                dto.idPlanoContratado(),
                dto.idAula(),
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
                dto.deficienciaDescricao(),
                dto.aulasRestantes()
        );
    }

    private AlunoDetalhadoComTreinosDTO consolidar(List<AlunoDetalhadoComTreinosDTO> duplicados) {
        AlunoDetalhadoComTreinosDTO base = duplicados.getFirst(); // os dados são iguais entre os duplicados

        // Horários e contagens agregadas por aluno (não por treino)
        List<String> horariosInicioSemana = aulaRepository.buscarHorariosInicioSemana(base.idAluno());
        List<String> horariosFimSemana = aulaRepository.buscarHorariosFimSemana(base.idAluno());
        List<String> horariosInicioTotal = aulaRepository.buscarHorariosInicioTotal(base.idAluno());
        List<String> horariosFimTotal = aulaRepository.buscarHorariosFimTotal(base.idAluno());

        Long treinosSemana = duplicados.stream().mapToLong(AlunoDetalhadoComTreinosDTO::treinosSemana).sum();
        Long treinosTotal = duplicados.stream().mapToLong(AlunoDetalhadoComTreinosDTO::treinosTotal).sum();

        Integer idPlanoContratado = duplicados.stream()
                .filter(dto -> dto.idPlanoContratado() != null &&
                        (
                                PeriodoEnum.AVULSO.equals(dto.periodoPlano()) ||
                                        (dto.dataVencimentoPlano() != null && !dto.dataVencimentoPlano().isBefore(LocalDate.now()))
                        )
                )
                .map(AlunoDetalhadoComTreinosDTO::idPlanoContratado)
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
