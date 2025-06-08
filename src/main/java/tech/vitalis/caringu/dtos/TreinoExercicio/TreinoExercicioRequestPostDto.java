        package tech.vitalis.caringu.dtos.TreinoExercicio;
        
        import jakarta.validation.constraints.NotNull;
        import jakarta.validation.constraints.Positive;
        import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
        import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;
        
        import java.time.LocalDateTime;
        
        public record TreinoExercicioRequestPostDto(
                Integer exercicioId,
                @Positive
                @NotNull(message = "Carga é obrigatória e não pode ser zero")
                Double carga,
                @Positive
                @NotNull(message = "Repetições é obrigatório e não pode ser zero")
                Integer repeticoes,
                @Positive
                @NotNull(message = "Series é obrigatória e não pode ser zero")
                Integer series,
                @Positive
                @NotNull(message = "Descanso é obrigatóri0 e não pode ser zero")
                Integer descanso,
                @NotNull(message = "Hora de Criaçãp é obrigatória")
                LocalDateTime dataHoraCriacao,
                LocalDateTime dataHoraModificacao,
                @NotNull(message = "O Origem do Treino Exercício é obrigatório.")
                OrigemTreinoExercicioEnum origemTreinoExercicio,
                @NotNull(message = "Grau de Dificuldade é obrigatório ")
                GrauDificuldadeEnum grauDificuldade
        ) {
        
        }
