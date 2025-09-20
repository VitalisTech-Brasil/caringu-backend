package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Notificacoes.*;
import tech.vitalis.caringu.service.NotificacaoPlanoVencimentoService;
import tech.vitalis.caringu.service.NotificacaoTreinoVencimentoService;
import tech.vitalis.caringu.service.NotificacoesService;

import java.time.LocalDate;
import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/notificacoes")
public class NotificacoesController {

    private final NotificacoesService notificacoesService;
    private final NotificacaoTreinoVencimentoService notificacaoTreinoVencimentoService;
    private final NotificacaoPlanoVencimentoService notificacaoPlanoVencimentoService;

    public NotificacoesController(NotificacoesService notificacoesService, NotificacaoTreinoVencimentoService notificacaoTreinoVencimentoService, NotificacaoPlanoVencimentoService notificacaoPlanoVencimentoService) {
        this.notificacoesService = notificacoesService;
        this.notificacaoTreinoVencimentoService = notificacaoTreinoVencimentoService;
        this.notificacaoPlanoVencimentoService = notificacaoPlanoVencimentoService;
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


//    @GetMapping("/treinos-vencendo")
//    public List<NotificacaoTreinoPersonalDTO> listarTreinosVencendo() {
//        LocalDate limite = LocalDate.now().plusWeeks(2);
//        return notificacaoTreinoVencimentoService.buscarTreinosVencendo(limite);
//    }
//
//    @GetMapping("/treinos-vencendo/{personalId}")
//    public List<NotificacaoTreinoPersonalDTO> listarTreinosVencendoPorPersonal(@PathVariable Integer personalId) {
//        LocalDate limite = LocalDate.now().plusWeeks(2);
//        return notificacaoTreinoVencimentoService.buscarTreinosVencendoPorPersonal(limite, personalId);
//    }

//    @GetMapping("/planos-vencendo/{personalId}")
//    public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimentoPorPersonal(@PathVariable Integer personalId){
//        LocalDate limite = LocalDate.now().plusWeeks(2);
//        return notificacaoPlanoVencimentoService.buscarNotificacoesPlanoVencimentoPorPersonal(limite, personalId);
//    }
      
   @PatchMapping("/{id}/visualizada")
   @Operation(summary = "Visualizar notificação")
    public ResponseEntity<Void> atualizarVisualizada(@PathVariable Integer id, @RequestBody NotificacaoVisualizadaRequestPatchDto dto){
        notificacoesService.atualizarVisualizada(id, dto.visualizada());
        return ResponseEntity.status(204).build();
    }

//    @GetMapping("/testar/notificacoes")
//    public ResponseEntity<String> testarNotificacoesManual() {
//        notificacaoTreinoVencimentoService.enviarNotificacoesTreinosVencendo();
//        notificacaoTreinoVencimentoService.notificarPersonaisTreinadores();
//        return ResponseEntity.ok("Notificações enviadas com sucesso!");
//    }


//    @GetMapping("/testar/notificacoes-plano")
//    public ResponseEntity<String> testarNotificacoesManualPlano() {
//        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();
//        notificacaoPlanoVencimentoService.notificarPersonais();
//        return ResponseEntity.ok("Notificações enviadas com sucesso!");
//    }


    @GetMapping("/pessoas/notificacoes-nao-visualizada/treino-vencimento/{id}")
    @Operation(summary = "Buscar todas as Notificações não visualizadas por pessoa do treino vencimento")
    public ResponseEntity<List<NotificacoesResponseGetDto>> buscarPorPessoaIdENaoVisualzaTreinoVencimento(@PathVariable Integer id){
        List<NotificacoesResponseGetDto> notificacoes = notificacoesService.buscarPorPessoaIdENaoVisualzaTreinoVencimento(id);
        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/pessoas/notificacoes-nao-visualizada/{id}")
    @Operation(summary = "Buscar todas as Notificações não visualizadas por pessoa")
    public ResponseEntity<List<NotificacoesResponseGetDto>> buscarPorPessoaIdENaoVisualza(@PathVariable Integer id){
        List<NotificacoesResponseGetDto> notificacoes = notificacoesService.buscarPorPessoaIdENaoVisualza(id);
        return ResponseEntity.ok(notificacoes);
    }

    @PatchMapping("/visualizar-todas/{pessoaId}")
    @Operation(summary = "Visualizar todas as notificações")
    public ResponseEntity<Void> marcarTodasComoVisualizadas(@PathVariable Integer pessoaId){
        notificacoesService.marcarTodasComoVisualizadasPorPessoaId(pessoaId);
        return ResponseEntity.status(204).build();
    }
  
      @GetMapping("/testar/notificacoes-plano")
    public ResponseEntity<String> testarNotificacoesManualPlano() {
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        return ResponseEntity.ok("Notificações enviadas com sucesso!");
    }
}
