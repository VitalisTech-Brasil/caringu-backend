package tech.vitalis.caringu.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.mapper.TreinoMapper;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.TreinoRepository;

class TreinoServiceTest {

    @Mock
    private TreinoRepository treinoRepository;

    @Mock
    private TreinoMapper treinoMapper;

    @Mock
    private PersonalTrainerRepository personalRepository;

    @InjectMocks
    private TreinoService treinoService;


    static final PersonalTrainerResponseGetDTO PERSONAL_TRAINER = new PersonalTrainerResponseGetDTO(
            101,
            "Carlos Silva",
            "carlos.silva@example.com",
            "11999999999",
            "https://example.com/fotoPerfil.jpg",
            LocalDate.of(1985, 6, 15),
            GeneroEnum.HOMEM_CISGENERO,
            "CREF123456",
            List.of(
                    new EspecialidadeResponseGetDTO(1,"Musculação"),
                    new EspecialidadeResponseGetDTO(2,"Treinamento Funcional")
            ),
            10,
            LocalDateTime.of(2020, 1, 10, 14, 30)
    );

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar o treino com sucesso")
    void CadastrarTreino_Successo() {
        TreinoRequestPostDTO requestDto = mock(TreinoRequestPostDTO.class);
        when(requestDto.personalId()).thenReturn(1);

        PersonalTrainer personal = new PersonalTrainer();
        Treino treinoEntity = new Treino();
        Treino treinoSalvo = new Treino();
        TreinoResponseGetDTO responseDto = new TreinoResponseGetDTO( 1,
                "Treino de Força",
                "Treino focado em ganho de massa muscular",
                PERSONAL_TRAINER);

        when(personalRepository.findById(1)).thenReturn(Optional.of(personal));
        when(treinoMapper.toEntity(requestDto)).thenReturn(treinoEntity);
        when(treinoRepository.save(any(Treino.class))).thenReturn(treinoSalvo);
        when(treinoMapper.toResponseDTO(treinoSalvo)).thenReturn(responseDto);

        TreinoResponseGetDTO result = treinoService.cadastrar(requestDto);

        assertNotNull(result);
        verify(personalRepository).findById(1);
        verify(treinoRepository).save(treinoEntity);
        verify(treinoMapper).toResponseDTO(treinoSalvo);
        assertEquals(responseDto, result);
    }

    @Test
    @DisplayName("Deve buscar treino por ID com sucesso")
    void BuscarPorId_Successo() {
        Treino treino = new Treino();
        TreinoResponseGetDTO responseDto = new TreinoResponseGetDTO( 1,
                "Treino de Força",
                "Treino focado em ganho de massa muscular",
                PERSONAL_TRAINER);
        when(treinoRepository.findById(1)).thenReturn(Optional.of(treino));
        when(treinoMapper.toResponseDTO(treino)).thenReturn(responseDto);

        TreinoResponseGetDTO result = treinoService.buscarPorId(1);

        assertNotNull(result);
        assertEquals(responseDto, result);
    }

    @Test
    @DisplayName("Deve retornar 5 treinos criados")
    void ObterQuantidadeTreinosCriados_RetornaContagem() {
        when(treinoRepository.countByPersonalId(1)).thenReturn(5);

        Integer count = treinoService.obterQuantidadeTreinosCriados(1);

        assertEquals(5, count);
    }

    @Test
    @DisplayName("Deve retornar lista de treinos com sucesso")
    void ListarTodos_RetornaLista() {
        Treino treino1 = new Treino();
        treino1.setId(1);

        Treino treino2 = new Treino();
        treino2.setId(2);
        List<Treino> treinos = Arrays.asList(treino1, treino2);

        TreinoResponseGetDTO dto1 = new TreinoResponseGetDTO( 1,
                "Treino de Força",
                "Treino focado em ganho de massa muscular",
                PERSONAL_TRAINER);

        TreinoResponseGetDTO dto2 = new TreinoResponseGetDTO( 2,
                "Treino de reabilitação",
                "Treino focado em reabilitar",
                PERSONAL_TRAINER);

        when(treinoRepository.findAll()).thenReturn(treinos);

        when(treinoMapper.toResponseDTO(any(Treino.class))).thenAnswer(invocation -> {
            Treino treino = invocation.getArgument(0);
            if (treino.getId() == 1) return dto1;
            if (treino.getId() == 2) return dto2;
            return null;
        });


        List<TreinoResponseGetDTO> result = treinoService.listarTodos();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    @DisplayName("Deve atualizar treino com sucesso")
    void Atualizar_Successo() {
        Treino treinoExistente = new Treino();
        PersonalTrainer personal = new PersonalTrainer();
        TreinoRequestUpdateDto updateDto = mock(TreinoRequestUpdateDto.class);
        when(updateDto.nome()).thenReturn("Treino Atualizado");
        when(updateDto.descricao()).thenReturn("Descricao Atualizada");

        Treino treinoAtualizado = new Treino();
        TreinoResponseGetDTO responseDto = new TreinoResponseGetDTO( 1,
                "Treino de Força",
                "Treino focado em ganho de massa muscular",
                PERSONAL_TRAINER);

        when(treinoRepository.findById(1)).thenReturn(Optional.of(treinoExistente));
        when(personalRepository.findById(1)).thenReturn(Optional.of(personal));
        when(treinoRepository.save(treinoExistente)).thenReturn(treinoAtualizado);
        when(treinoMapper.toResponseDTO(treinoAtualizado)).thenReturn(responseDto);

        TreinoResponseGetDTO result = treinoService.atualizar(1, updateDto, 1);

        assertNotNull(result);
        assertEquals(responseDto, result);
        assertEquals("Treino Atualizado", treinoExistente.getNome());
        assertEquals("Descricao Atualizada", treinoExistente.getDescricao());
        assertEquals(personal, treinoExistente.getPersonal());
    }

    @Test
    @DisplayName("Deve remover treino com sucesso")
    void Remover_Successo() {
        Treino treinoExistente = new Treino();

        when(treinoRepository.findById(1)).thenReturn(Optional.of(treinoExistente));

        treinoService.remover(1);

        verify(treinoRepository).deleteById(1);
        assertNull(treinoExistente.getPersonal());
    }

}
