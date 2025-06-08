package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.TreinoFinalizado.*;
import tech.vitalis.caringu.service.TreinoFinalizadoService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/treinos-finalizados")
public class TreinoFinalizadoController {

    private final TreinoFinalizadoService service;

    public TreinoFinalizadoController(TreinoFinalizadoService service) {
        this.service = service;
    }

    @GetMapping("/personal/{personalId}")
    public ResponseEntity<List<TreinoIdentificacaoFinalizadoResponseDTO>> listarTreinosPorPersonal(@PathVariable Integer personalId) {
        List<TreinoIdentificacaoFinalizadoResponseDTO> treinos = service.listarPorPersonal(personalId);
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/evolucao-carga")
    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(
            @RequestParam Integer alunoId,
            @RequestParam Integer exercicioId
    ) {
        return service.buscarEvolucaoCarga(alunoId, exercicioId);
    }

    @GetMapping("/evolucao-treinos-cumpridos")
    public ResponseEntity<List<EvolucaoTreinoCumpridoResponseDTO>> buscarEvolucao(
            @RequestParam Integer alunoId,
            @RequestParam Integer exercicioId
    ) {
        List<EvolucaoTreinoCumpridoResponseDTO> resultado = service.buscarEvolucaoTreinosCumpridosMensal(alunoId, exercicioId);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/horas-treinadas")
    public ResponseEntity<HorasTreinadasResponseDTO> buscarHorasTreinadas(
            @RequestParam Integer alunoId,
            @RequestParam Integer exercicioId
    ) {
        HorasTreinadasResponseDTO resultado = service.buscarHorasTreinadas(alunoId, exercicioId);
        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/{idTreinoFinalizado}/finalizar")
    public ResponseEntity<Void> finalizarTreino(@PathVariable Integer idTreinoFinalizado, @RequestBody AtualizarDataFimDTO dto) {
        service.atualizarDataHorarioFim(idTreinoFinalizado, dto);
        return ResponseEntity.noContent().build();
    }
}
