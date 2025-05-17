package tech.vitalis.caringu.controller;

import com.azure.core.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPatchDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPostDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesResponseGetDto;
import tech.vitalis.caringu.service.NotificacoesService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/notificacoes")
public class NotificacoesController {

    private final NotificacoesService notificacoesService;

    public NotificacoesController(NotificacoesService notificacoesService) {
        this.notificacoesService = notificacoesService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova notificação")
    public ResponseEntity<NotificacoesResponseGetDto> cadastrar(@Valid @RequestBody NotificacoesRequestPostDto notificacoesDto){
        NotificacoesResponseGetDto notificacaoCriada = notificacoesService.cadastrar(notificacoesDto);

        return ResponseEntity.status(201).body(notificacaoCriada);
    }

    @GetMapping
    @Operation(summary = "Buscar todas as Notificações")
    public ResponseEntity<List<NotificacoesResponseGetDto>> listarTodos(){
        return ResponseEntity.ok(notificacoesService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Notificações por Id")
    public ResponseEntity<NotificacoesResponseGetDto> buscarPorId(@PathVariable Integer id){
        return  ResponseEntity.ok(notificacoesService.buscarPorId(id));
    }

    @GetMapping("/pessoas/{id}")
    @Operation(summary = "Buscar Notificações por Pessoa")
    public ResponseEntity<List<NotificacoesResponseGetDto>> buscarPorPessoasId(@PathVariable Integer id) {
        return ResponseEntity.ok(notificacoesService.buscarPorPessoaId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Notificação")
    public ResponseEntity<NotificacoesResponseGetDto> atualizar(@PathVariable Integer id, @Valid @RequestBody NotificacoesRequestPatchDto notificacoesDto){
        return ResponseEntity.ok(notificacoesService.atualizar(id, notificacoesDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover Notificação")
    public ResponseEntity<Void> removerNotificacao(@PathVariable Integer id){
        notificacoesService.removerAssociacaoComPessoa(id);
        notificacoesService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
