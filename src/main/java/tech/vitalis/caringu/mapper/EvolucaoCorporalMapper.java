package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalRequestPostDTO;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.EvolucaoCorporal;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.Duration;

@Component
public class EvolucaoCorporalMapper {

    private final AlunoMapper alunoMapper;
    private final ArmazenamentoService armazenamentoInterface;

    public EvolucaoCorporalMapper(AlunoMapper alunoMapper, ArmazenamentoService armazenamentoInterface) {
        this.alunoMapper = alunoMapper;
        this.armazenamentoInterface = armazenamentoInterface;
    }

    public EvolucaoCorporal toEntity(EvolucaoCorporalRequestPostDTO dto, Aluno aluno) {
        EvolucaoCorporal ec = new EvolucaoCorporal();
        ec.setTipo(dto.tipo());
        ec.setUrlFotoShape(dto.urlFotoShape());
        ec.setDataEnvio(dto.dataEnvio());
        ec.setPeriodoAvaliacao(dto.periodoAvaliacao());
        return ec;
    }

    public EvolucaoCorporalResponseGetDTO toResponseDTO(EvolucaoCorporal ec) {
        String urlFotoTemporaria = ec.getUrlFotoShape() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(ec.getUrlFotoShape(), Duration.ofMinutes(5))
                : null;

        return new EvolucaoCorporalResponseGetDTO(
                ec.getId(),
                ec.getTipo(),
                urlFotoTemporaria,
                ec.getDataEnvio(),
                ec.getPeriodoAvaliacao()
        );
    }
}
