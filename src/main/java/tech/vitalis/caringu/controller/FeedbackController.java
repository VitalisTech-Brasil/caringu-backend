package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aula.Response.AulasAlunoFeedbackResponseDTO;
import tech.vitalis.caringu.dtos.Feedback.CriacaoFeedbackDto;
import tech.vitalis.caringu.dtos.Feedback.FeedbackItemDTO;
import tech.vitalis.caringu.dtos.Feedback.FeedbackPorDataDTO;
import tech.vitalis.caringu.dtos.Feedback.RespostaFeedbackDto;
import tech.vitalis.caringu.service.FeedbackService;

import java.time.LocalDate;
import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<FeedbackPorDataDTO>> buscarFeedbackPorAula(@PathVariable Integer aulaId){
        List<FeedbackPorDataDTO> feedbacks = feedbackService.buscarFeedbacksPorAula(aulaId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/aluno/{idAluno}/aulas")
    public ResponseEntity<Page<AulasAlunoFeedbackResponseDTO>> listarFeedbacksPorAlunoComPlano(
            @PathVariable Integer idAluno,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        Page<AulasAlunoFeedbackResponseDTO> aulas = feedbackService.listarFeedbacksPorAlunoComPlano(
                idAluno,
                PageRequest.of(page, size),
                data
        );

        return ResponseEntity.ok(aulas);
    }

    @PostMapping
    public ResponseEntity<RespostaFeedbackDto> criarFeedback(
            @RequestBody CriacaoFeedbackDto dto) {

        return ResponseEntity.ok(feedbackService.criarFeedback(dto));
    }
}
