package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.PreferenciaNotificacao.PreferenciaNotificacaoRequestPutDTO;
import tech.vitalis.caringu.dtos.PreferenciaNotificacao.PreferenciaNotificacaoResponseGetDTO;
import tech.vitalis.caringu.mapper.PreferenciaNotificacaoMapper;
import tech.vitalis.caringu.service.PreferenciaNotificacaoService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/preferencias-notificacao")
public class PreferenciaNotificacaoController {

    private final PreferenciaNotificacaoService service;
    private final PreferenciaNotificacaoMapper mapper;

    public PreferenciaNotificacaoController(PreferenciaNotificacaoService service, PreferenciaNotificacaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<List<PreferenciaNotificacaoResponseGetDTO>> listar(@PathVariable Integer pessoaId) {
        var preferencias = service.listarPorPessoa(pessoaId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(preferencias);
    }

    @PutMapping("/{pessoaId}")
    @Operation(
            summary = "Atualizar status de uma preferência de notificação",
            description = """
        Atualiza o status (ativada/desativada) de um tipo específico de notificação para o usuário informado.
        
        ⚠️ A preferência precisa já existir (ela é criada automaticamente ao cadastrar um Personal Trainer).
        
        🧾 Exemplo de payload:
        ```json
        {
          "tipo": "PAGAMENTO_REALIZADO",
          "ativada": false
        }
        ```
        """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = PreferenciaNotificacaoResponseGetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Preferência não encontrada para essa pessoa ou tipo",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados no corpo da requisição",
                    content = @Content)
    })
    public ResponseEntity<PreferenciaNotificacaoResponseGetDTO> atualizar(@PathVariable Integer pessoaId,
                                                                       @RequestBody @Valid PreferenciaNotificacaoRequestPutDTO dto) {

        var atualizada = service.atualizarPreferencia(pessoaId, dto.tipo(), dto.ativada());
        return ResponseEntity.ok(mapper.toResponseDTO(atualizada));
    }
}