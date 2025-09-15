package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.service.TreinoFinalizadoService;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/treinos-finalizados")
public class TreinoFinalizadoController {

    private final TreinoFinalizadoService service;

    public TreinoFinalizadoController(TreinoFinalizadoService service) {
        this.service = service;
    }

    // ESSE JÁ FOI FEITO - PROCURAR POR: sessaoTreinoService.listarAulasPorPersonal
//    @GetMapping("/personal/{personalId}")
//    public ResponseEntity<List<TreinoIdentificacaoFinalizadoResponseDTO>> listarTreinosPorPersonal(@PathVariable Integer personalId) {
//        List<TreinoIdentificacaoFinalizadoResponseDTO> treinos = service.listarPorPersonal(personalId);
//        return ResponseEntity.ok(treinos);
//    }

    // TEM QUE FAZER
//    @GetMapping("/evolucao-carga")
//    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(
//            @RequestParam Integer alunoId,
//            @RequestParam Integer exercicioId
//    ) {
//        return service.buscarEvolucaoCarga(alunoId, exercicioId);
//    }

    // TEM QUE FAZER
//    @GetMapping("/evolucao-treinos-cumpridos")
//    public ResponseEntity<List<EvolucaoTreinoCumpridoResponseDTO>> buscarEvolucao(
//            @RequestParam Integer alunoId,
//            @RequestParam Integer exercicioId
//    ) {
//        List<EvolucaoTreinoCumpridoResponseDTO> resultado = service.buscarEvolucaoTreinosCumpridosMensal(alunoId, exercicioId);
//        return ResponseEntity.ok(resultado);
//    }

    // TEM QUE FAZER
//    @GetMapping("/horas-treinadas")
//    public ResponseEntity<HorasTreinadasResponseDTO> buscarHorasTreinadas(
//            @RequestParam Integer alunoId,
//            @RequestParam Integer exercicioId
//    ) {
//        HorasTreinadasResponseDTO resultado = service.buscarHorasTreinadas(alunoId, exercicioId);
//        return ResponseEntity.ok(resultado);
//    }

    // ESSE JÁ FOI FEITO - PROCURAR POR: sessaoTreinoService.atualizarStatus
//    @PatchMapping("/{idTreinoFinalizado}/finalizar")
//    public ResponseEntity<Void> finalizarTreino(@PathVariable Integer idTreinoFinalizado, @RequestBody AtualizarDataFimDTO dto) {
//        service.atualizarDataHorarioFim(idTreinoFinalizado, dto);
//        return ResponseEntity.noContent().build();
//    }
}
