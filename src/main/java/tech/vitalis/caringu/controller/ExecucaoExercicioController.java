package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.service.ExecucaoExercicioService;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/execucoes-exercicios")
public class ExecucaoExercicioController {

    public final ExecucaoExercicioService execucaoExercicioService;

    public ExecucaoExercicioController(ExecucaoExercicioService execucaoExercicioService) {
        this.execucaoExercicioService = execucaoExercicioService;
    }
}
