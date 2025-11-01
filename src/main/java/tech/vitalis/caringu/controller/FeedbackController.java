package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.dtos.Feedback.FeedbackPorDataDTO;
import tech.vitalis.caringu.service.FeedbackService;

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
}
