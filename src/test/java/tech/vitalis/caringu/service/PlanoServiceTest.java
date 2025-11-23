package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.core.adapter.planoContratado.PlanoContratadoDTOMapper;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoRequisicaoRecord;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.infrastructure.web.planoContratado.PlanoContratadoResponse;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.mapper.PlanoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PlanoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanoServiceTest {

    @Mock
    private PlanoRepository planoRepository;

    @Mock
    private PlanoMapper planoMapper;

    @Mock
    private PlanoContratadoRepository planoContratadoRepository;

    @Mock
    private PlanoContratadoDTOMapper planoContratadoDTOMapper;

    @Mock
    private PersonalTrainerService personalTrainerService;

    @Mock
    private PersonalTrainerMapper personalTrainerMapper;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private PlanoService planoService;

    @Test
    @DisplayName("listarPlanosPorPersonal deve retornar lista mapeada")
    void listarPlanosPorPersonal_ComSucesso() {
        Plano plano = new Plano();
        when(planoRepository.findByPersonalTrainerId(1)).thenReturn(List.of(plano));

        PlanoRespostaRecord resposta = new PlanoRespostaRecord(1, "Plano Básico", PeriodoEnum.MENSAL, 10, 99.9);
        when(planoMapper.PlanoListToResponseList(List.of(plano)))
                .thenReturn(List.of(resposta));

        List<PlanoRespostaRecord> resultado = planoService.listarPlanosPorPersonal(1);

        assertEquals(1, resultado.size());
        verify(planoRepository).findByPersonalTrainerId(1);
    }

    @Test
    @DisplayName("cadastrarPlano deve criar plano para personal")
    void cadastrarPlano_ComSucesso() {
        PlanoRequisicaoRecord req = new PlanoRequisicaoRecord("Plano X", PeriodoEnum.MENSAL, 10, 99.9);
        PersonalTrainerResponseGetDTO personalDTO = mock(PersonalTrainerResponseGetDTO.class);
        PersonalTrainer personal = new PersonalTrainer();

        when(personalTrainerService.buscarPorId(1)).thenReturn(personalDTO);
        when(personalTrainerMapper.responseToEntity(personalDTO)).thenReturn(personal);

        Plano plano = new Plano();
        when(planoMapper.requestToEntity(req, personal)).thenReturn(plano);

        PlanoRespostaRecord resposta = new PlanoRespostaRecord(1, "Plano X", PeriodoEnum.MENSAL, 10, 99.9);
        when(planoMapper.toResponseRecord(plano)).thenReturn(resposta);

        PlanoRespostaRecord resultado = planoService.cadastrarPlano(1, req);

        assertNotNull(resultado);
        verify(planoRepository).save(plano);
    }

    @Test
    @DisplayName("atualizarPlano deve atualizar campos básicos do plano")
    void atualizarPlano_ComSucesso() {
        Plano plano = new Plano();
        plano.setId(1);

        when(planoRepository.findByPersonalTrainerIdAndIdEquals(1, 1))
                .thenReturn(Optional.of(plano));

        PlanoRequisicaoRecord req = new PlanoRequisicaoRecord("Novo Plano", PeriodoEnum.SEMESTRAL, 20, 199.9);
        PlanoRespostaRecord resposta = new PlanoRespostaRecord(1, "Novo Plano", PeriodoEnum.SEMESTRAL, 20, 199.9);
        when(planoRepository.save(plano)).thenReturn(plano);
        when(planoMapper.toResponseRecord(plano)).thenReturn(resposta);

        PlanoRespostaRecord resultado = planoService.atualizarPlano(1, req, 1);

        assertEquals("Novo Plano", plano.getNome());
        assertEquals(PeriodoEnum.SEMESTRAL, plano.getPeriodo());
        verify(planoRepository).save(plano);
    }

    @Test
    @DisplayName("atualizarPlano deve lançar ResourceNotFoundException quando plano não pertencer ao personal")
    void atualizarPlano_PlanosNaoEncontrado() {
        when(planoRepository.findByPersonalTrainerIdAndIdEquals(1, 1))
                .thenReturn(Optional.empty());

        PlanoRequisicaoRecord req = new PlanoRequisicaoRecord("Plano", PeriodoEnum.MENSAL, 10, 99.9);

        assertThrows(ApiExceptions.ResourceNotFoundException.class,
                () -> planoService.atualizarPlano(1, req, 1));
    }

    @Test
    @DisplayName("deletarPlano deve deletar plano existente")
    void deletarPlano_ComSucesso() {
        Plano plano = new Plano();
        when(planoRepository.findByPersonalTrainerIdAndIdEquals(1, 1))
                .thenReturn(Optional.of(plano));

        planoService.deletarPlano(1, 1);

        verify(planoRepository).deleteById(1);
    }

    @Test
    @DisplayName("contratarPlano deve criar plano contratado com datas e status corretos para MENSAL")
    void contratarPlano_Mensal() {
        Aluno aluno = new Aluno();
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));

        Plano plano = new Plano();
        plano.setPeriodo(PeriodoEnum.MENSAL);
        when(planoRepository.findById(2)).thenReturn(Optional.of(plano));

        PlanoContratadoEntity contratado = new PlanoContratadoEntity();
        when(planoContratadoRepository.save(any(PlanoContratadoEntity.class))).thenAnswer(invocation -> {
            PlanoContratadoEntity entity = invocation.getArgument(0);
            contratado.setAluno(entity.getAluno());
            contratado.setPlano(entity.getPlano());
            contratado.setStatus(entity.getStatus());
            contratado.setDataContratacao(entity.getDataContratacao());
            contratado.setDataFim(entity.getDataFim());
            return contratado;
        });

        // Não precisamos dos detalhes do response aqui, apenas garantir que o mapper é chamado
        PlanoContratadoResponse resposta = mock(PlanoContratadoResponse.class);
        when(planoContratadoDTOMapper.toResponseContratarPlano(any())).thenReturn(resposta);

        PlanoContratadoResponse resultado = planoService.contratarPlano(1, 2);

        assertNotNull(resultado);
        assertEquals(StatusEnum.PENDENTE, contratado.getStatus());
        assertNotNull(contratado.getDataFim());
    }
}


