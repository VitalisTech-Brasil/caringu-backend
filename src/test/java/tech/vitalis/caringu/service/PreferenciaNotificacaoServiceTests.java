package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.PreferenciaNotificacao;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.exception.PreferenciasNotificacao.PreferenciasNotificacaoNaoEncontradaException;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreferenciaNotificacaoServiceTests {

    @Mock
    private PreferenciaNotificacaoRepository repository;

    @InjectMocks
    private PreferenciaNotificacaoService preferenciaNotificacaoService;

    @Captor
    private ArgumentCaptor<List<PreferenciaNotificacao>> preferenciasListCaptor;

    @Captor
    private ArgumentCaptor<PreferenciaNotificacao> preferenciaCaptor;

    private Pessoa pessoa;
    private PreferenciaNotificacao preferencia;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João Silva");

        preferencia = new PreferenciaNotificacao();
        preferencia.setId(1);
        preferencia.setPessoa(pessoa);
        preferencia.setTipo(TipoPreferenciaEnum.AULAS_PENDENTES);
        preferencia.setAtivada(true);
    }

    @Test
    @DisplayName("listarPorPessoa deve retornar lista de preferências quando existem")
    void listarPorPessoa_ComPreferencias_DeveRetornarLista() {
        // ARRANGE
        when(repository.findByPessoaId(1)).thenReturn(List.of(preferencia));

        // ACT
        List<PreferenciaNotificacao> resultado = preferenciaNotificacaoService.listarPorPessoa(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(preferencia, resultado.get(0));
        verify(repository).findByPessoaId(1);
    }

    @Test
    @DisplayName("listarPorPessoa deve retornar lista vazia quando não há preferências")
    void listarPorPessoa_SemPreferencias_DeveRetornarListaVazia() {
        // ARRANGE
        when(repository.findByPessoaId(1)).thenReturn(Collections.emptyList());

        // ACT
        List<PreferenciaNotificacao> resultado = preferenciaNotificacaoService.listarPorPessoa(1);

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).findByPessoaId(1);
    }

    @Test
    @DisplayName("listarPorPessoa deve retornar múltiplas preferências")
    void listarPorPessoa_ComMultiplasPreferencias_DeveRetornarTodasPreferencias() {
        // ARRANGE
        PreferenciaNotificacao preferencia2 = new PreferenciaNotificacao();
        preferencia2.setId(2);
        preferencia2.setPessoa(pessoa);
        preferencia2.setTipo(TipoPreferenciaEnum.PAGAMENTO_REALIZADO);
        preferencia2.setAtivada(false);

        when(repository.findByPessoaId(1)).thenReturn(List.of(preferencia, preferencia2));

        // ACT
        List<PreferenciaNotificacao> resultado = preferenciaNotificacaoService.listarPorPessoa(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("criarPreferenciasPadrao deve criar preferência para cada tipo de enum")
    void criarPreferenciasPadrao_DeveCriarTodasPreferenciasPadrao() {
        // ARRANGE
        int totalTipos = TipoPreferenciaEnum.values().length;

        // ACT
        preferenciaNotificacaoService.criarPreferenciasPadrao(pessoa);

        // ASSERT
        verify(repository).saveAll(preferenciasListCaptor.capture());
        List<PreferenciaNotificacao> preferenciasSalvas = preferenciasListCaptor.getValue();

        assertEquals(totalTipos, preferenciasSalvas.size());

        // Verificar que todas estão ativadas por padrão
        preferenciasSalvas.forEach(pref -> {
            assertTrue(pref.isAtivada());
            assertEquals(pessoa, pref.getPessoa());
            assertNotNull(pref.getTipo());
        });
    }

    @Test
    @DisplayName("criarPreferenciasPadrao deve criar todas as preferências com pessoa associada")
    void criarPreferenciasPadrao_DeveAssociarPessoaATodas() {

        // ACT
        preferenciaNotificacaoService.criarPreferenciasPadrao(pessoa);

        // ASSERT
        verify(repository).saveAll(preferenciasListCaptor.capture());
        List<PreferenciaNotificacao> preferenciasSalvas = preferenciasListCaptor.getValue();

        preferenciasSalvas.forEach(pref -> assertEquals(pessoa, pref.getPessoa()));
    }

    @Test
    @DisplayName("criarPreferenciasPadrao deve criar preferências para todos os tipos de enum existentes")
    void criarPreferenciasPadrao_DeveCriarParaTodosTiposDeEnum() {
        // ARRANGE
        TipoPreferenciaEnum[] todosTipos = TipoPreferenciaEnum.values();

        // ACT
        preferenciaNotificacaoService.criarPreferenciasPadrao(pessoa);

        // ASSERT
        verify(repository).saveAll(preferenciasListCaptor.capture());
        List<PreferenciaNotificacao> preferenciasSalvas = preferenciasListCaptor.getValue();

        // Verificar se todos os tipos foram criados
        for (TipoPreferenciaEnum tipo : todosTipos) {
            boolean existeTipo = preferenciasSalvas.stream()
                    .anyMatch(pref -> pref.getTipo() == tipo);
            assertTrue(existeTipo, "Tipo " + tipo + " não foi criado");
        }
    }

    @Test
    @DisplayName("criarPreferenciasPadrao deve criar todas as preferências como ativadas")
    void criarPreferenciasPadrao_DeveIniciarTodasComoAtivadas() {

        // ACT
        preferenciaNotificacaoService.criarPreferenciasPadrao(pessoa);

        // ASSERT
        verify(repository).saveAll(preferenciasListCaptor.capture());
        List<PreferenciaNotificacao> preferenciasSalvas = preferenciasListCaptor.getValue();

        long todasAtivadas = preferenciasSalvas.stream()
                .filter(PreferenciaNotificacao::isAtivada)
                .count();

        assertEquals(preferenciasSalvas.size(), todasAtivadas);
    }

    @Test
    @DisplayName("atualizarPreferencia deve atualizar preferência existente para ativada")
    void atualizarPreferencia_PreferenciaExiste_DeveAtualizarParaAtivada() {
        // ARRANGE
        preferencia.setAtivada(false);
        when(repository.findByPessoaId(1)).thenReturn(List.of(preferencia));
        when(repository.save(any(PreferenciaNotificacao.class))).thenReturn(preferencia);

        // ACT
        PreferenciaNotificacao resultado = preferenciaNotificacaoService.atualizarPreferencia(
                1,
                TipoPreferenciaEnum.AULAS_PENDENTES,
                true
        );

        // ASSERT
        assertNotNull(resultado);
        verify(repository).save(preferenciaCaptor.capture());
        PreferenciaNotificacao preferenciaAtualizada = preferenciaCaptor.getValue();

        assertTrue(preferenciaAtualizada.isAtivada());
        assertEquals(TipoPreferenciaEnum.AULAS_PENDENTES, preferenciaAtualizada.getTipo());
    }

    @Test
    @DisplayName("atualizarPreferencia deve atualizar preferência existente para desativada")
    void atualizarPreferencia_PreferenciaExiste_DeveAtualizarParaDesativada() {
        // ARRANGE
        when(repository.findByPessoaId(1)).thenReturn(List.of(preferencia));
        when(repository.save(any(PreferenciaNotificacao.class))).thenReturn(preferencia);

        // ACT
        PreferenciaNotificacao resultado = preferenciaNotificacaoService.atualizarPreferencia(
                1,
                TipoPreferenciaEnum.AULAS_PENDENTES,
                false
        );

        // ASSERT
        assertNotNull(resultado);
        verify(repository).save(preferenciaCaptor.capture());
        PreferenciaNotificacao preferenciaAtualizada = preferenciaCaptor.getValue();

        assertFalse(preferenciaAtualizada.isAtivada());
    }

    @Test
    @DisplayName("atualizarPreferencia deve lançar exceção quando preferência não existe")
    void atualizarPreferencia_PreferenciaNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(repository.findByPessoaId(1)).thenReturn(Collections.emptyList());

        // ACT & ASSERT
        PreferenciasNotificacaoNaoEncontradaException exception = assertThrows(
                PreferenciasNotificacaoNaoEncontradaException.class,
                () -> preferenciaNotificacaoService.atualizarPreferencia(
                        1,
                        TipoPreferenciaEnum.AULAS_PENDENTES,
                        true
                )
        );

        assertEquals("Preferência não encontrada.", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarPreferencia deve lançar exceção quando tipo específico não existe")
    void atualizarPreferencia_TipoNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        PreferenciaNotificacao outraPreferencia = new PreferenciaNotificacao();
        outraPreferencia.setId(2);
        outraPreferencia.setPessoa(pessoa);
        outraPreferencia.setTipo(TipoPreferenciaEnum.PAGAMENTO_REALIZADO);
        outraPreferencia.setAtivada(true);

        when(repository.findByPessoaId(1)).thenReturn(List.of(outraPreferencia));

        // ACT & ASSERT
        PreferenciasNotificacaoNaoEncontradaException exception = assertThrows(
                PreferenciasNotificacaoNaoEncontradaException.class,
                () -> preferenciaNotificacaoService.atualizarPreferencia(
                        1,
                        TipoPreferenciaEnum.AULAS_PENDENTES, // Tipo diferente
                        true
                )
        );

        assertEquals("Preferência não encontrada.", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarPreferencia deve encontrar preferência correta quando há múltiplas")
    void atualizarPreferencia_ComMultiplasPreferencias_DeveAtualizarCorreta() {
        // ARRANGE
        PreferenciaNotificacao preferencia2 = new PreferenciaNotificacao();
        preferencia2.setId(2);
        preferencia2.setPessoa(pessoa);
        preferencia2.setTipo(TipoPreferenciaEnum.PAGAMENTO_REALIZADO);
        preferencia2.setAtivada(true);

        PreferenciaNotificacao preferencia3 = new PreferenciaNotificacao();
        preferencia3.setId(3);
        preferencia3.setPessoa(pessoa);
        preferencia3.setTipo(TipoPreferenciaEnum.FEEDBACK_TREINO);
        preferencia3.setAtivada(true);

        when(repository.findByPessoaId(1)).thenReturn(List.of(preferencia, preferencia2, preferencia3));
        when(repository.save(any(PreferenciaNotificacao.class))).thenReturn(preferencia2);

        // ACT
        PreferenciaNotificacao resultado = preferenciaNotificacaoService.atualizarPreferencia(
                1,
                TipoPreferenciaEnum.PAGAMENTO_REALIZADO,
                false
        );

        // ASSERT
        assertNotNull(resultado);
        verify(repository).save(preferenciaCaptor.capture());
        PreferenciaNotificacao preferenciaAtualizada = preferenciaCaptor.getValue();

        assertEquals(TipoPreferenciaEnum.PAGAMENTO_REALIZADO, preferenciaAtualizada.getTipo());
        assertFalse(preferenciaAtualizada.isAtivada());
    }
}
