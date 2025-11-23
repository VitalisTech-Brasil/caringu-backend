package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostFuncionalDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.ExercicioMapper;
import tech.vitalis.caringu.repository.ExercicioRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExercicioServiceTest {

    @Mock
    private TreinoExercicioRepository treinoExercicioRepository;

    @Mock
    private PersonalTrainerRepository personalTrainerRepository;

    @Mock
    private ExercicioRepository exercicioRepository;

    @Mock
    private ExercicioMapper exercicioMapper;

    @InjectMocks
    private ExercicioService exercicioService;

    @Test
    @DisplayName("listarExerciciosPorIdPersonal (sem filtro) deve retornar todos os exercícios mapeados")
    void listarExerciciosPorIdPersonal_SemFiltro() {
        Exercicio exercicio = new Exercicio();
        ExercicioResponseGetDTO dto = mock(ExercicioResponseGetDTO.class);

        when(exercicioRepository.findAll()).thenReturn(List.of(exercicio));
        when(exercicioMapper.toResponseDTO(exercicio)).thenReturn(dto);

        List<ExercicioResponseGetDTO> resultado = exercicioService.listarExerciciosPorIdPersonal();

        assertEquals(1, resultado.size());
        verify(exercicioRepository).findAll();
        verify(exercicioMapper).toResponseDTO(exercicio);
    }

    @Test
    @DisplayName("buscarPorId deve retornar DTO quando exercício existir")
    void buscarPorId_ComSucesso() {
        Exercicio exercicio = new Exercicio();
        when(exercicioRepository.findById(1)).thenReturn(Optional.of(exercicio));
        ExercicioResponseGetDTO dto = mock(ExercicioResponseGetDTO.class);
        when(exercicioMapper.toResponseDTO(exercicio)).thenReturn(dto);

        ExercicioResponseGetDTO resultado = exercicioService.buscarPorId(1);

        assertNotNull(resultado);
        verify(exercicioRepository).findById(1);
        verify(exercicioMapper).toResponseDTO(exercicio);
    }

    @Test
    @DisplayName("buscarPorId deve lançar exceção quando exercício não existir")
    void buscarPorId_NaoEncontrado() {
        when(exercicioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ApiExceptions.ResourceNotFoundException.class,
                () -> exercicioService.buscarPorId(1));
    }

    @Test
    @DisplayName("paginarExerciciosPorIdPersonal deve mapear página de exercícios")
    void paginarExerciciosPorIdPersonal_ComSucesso() {
        Exercicio exercicio = new Exercicio();
        Page<Exercicio> page = new PageImpl<>(List.of(exercicio));
        when(exercicioRepository.findAllByPersonal_IdOrPersonalIsNull(eq(1), any(PageRequest.class)))
                .thenReturn(page);

        ExercicioResponseGetDTO dto = mock(ExercicioResponseGetDTO.class);
        when(exercicioMapper.toResponseDTO(exercicio)).thenReturn(dto);

        Page<ExercicioResponseGetDTO> resultado =
                exercicioService.paginarExerciciosPorIdPersonal(1, PageRequest.of(0, 10));

        assertEquals(1, resultado.getTotalElements());
        verify(exercicioRepository).findAllByPersonal_IdOrPersonalIsNull(eq(1), any(PageRequest.class));
    }

    @Test
    @DisplayName("cadastrar deve criar exercício funcional para um personal")
    void cadastrar_ComSucesso() {
        ExercicioRequestPostFuncionalDTO request = new ExercicioRequestPostFuncionalDTO(
                5,
                "Agachamento Livre",
                GrupoMuscularEnum.PERNAS,
                "https://video.com/agachamento",
                "Manter postura"
        );

        Exercicio exercicio = new Exercicio();
        when(exercicioMapper.toEntity(request)).thenReturn(exercicio);

        PersonalTrainer personal = new PersonalTrainer();
        when(personalTrainerRepository.findById(5)).thenReturn(Optional.of(personal));

        Exercicio salvo = new Exercicio();
        when(exercicioRepository.save(exercicio)).thenReturn(salvo);

        ExercicioResponseGetDTO dto = mock(ExercicioResponseGetDTO.class);
        when(exercicioMapper.toResponseDTO(salvo)).thenReturn(dto);

        ExercicioResponseGetDTO resultado = exercicioService.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(personal, exercicio.getPersonal());
        assertEquals(OrigemEnum.PERSONAL, exercicio.getOrigem());
        verify(exercicioRepository).save(exercicio);
    }

    @Test
    @DisplayName("editarInfoExercicio deve atualizar campos válidos")
    void editarInfoExercicio_ComSucesso() {
        Exercicio exercicio = new Exercicio();
        when(exercicioRepository.findById(1)).thenReturn(Optional.of(exercicio));

        ExercicioRequestPostDTO request = new ExercicioRequestPostDTO(
                "Supino Reto",
                GrupoMuscularEnum.PEITORAL,
                null,
                null,
                null,
                OrigemEnum.BIBLIOTECA
        );

        Exercicio salvo = new Exercicio();
        when(exercicioRepository.save(exercicio)).thenReturn(salvo);
        ExercicioResponseGetDTO dto = mock(ExercicioResponseGetDTO.class);
        when(exercicioMapper.toResponseDTO(salvo)).thenReturn(dto);

        ExercicioResponseGetDTO resultado = exercicioService.editarInfoExercicio(1, request);

        assertNotNull(resultado);
        assertEquals("Supino Reto", exercicio.getNome());
        assertEquals(GrupoMuscularEnum.PEITORAL, exercicio.getGrupoMuscular());
        verify(exercicioRepository).save(exercicio);
    }

    @Test
    @DisplayName("editarInfoExercicio deve lançar BadRequest quando nenhum campo for válido")
    void editarInfoExercicio_SemCamposValidos() {
        Exercicio exercicio = new Exercicio();
        when(exercicioRepository.findById(1)).thenReturn(Optional.of(exercicio));

        ExercicioRequestPostDTO request = new ExercicioRequestPostDTO(
                null,
                null,
                null,
                null,
                null,
                OrigemEnum.BIBLIOTECA
        );

        assertThrows(ApiExceptions.BadRequestException.class,
                () -> exercicioService.editarInfoExercicio(1, request));

        verify(exercicioRepository, never()).save(any());
    }

    @Test
    @DisplayName("remover deve limpar vínculos em TreinoExercicio e deletar exercício")
    void remover_ComSucesso() {
        when(exercicioRepository.existsById(1)).thenReturn(true);

        TreinoExercicio treinoExercicio = new TreinoExercicio();
        when(treinoExercicioRepository.findAllByExercicioId(1))
                .thenReturn(List.of(treinoExercicio));

        exercicioService.remover(1);

        assertNull(treinoExercicio.getExercicio());
        verify(treinoExercicioRepository).findAllByExercicioId(1);
        verify(exercicioRepository).deleteById(1);
    }

    @Test
    @DisplayName("remover deve lançar exceção quando exercício não existir")
    void remover_NaoEncontrado() {
        when(exercicioRepository.existsById(1)).thenReturn(false);

        assertThrows(ApiExceptions.ResourceNotFoundException.class,
                () -> exercicioService.remover(1));

        verify(exercicioRepository, never()).deleteById(anyInt());
    }
}


