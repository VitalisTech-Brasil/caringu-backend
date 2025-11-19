package tech.vitalis.caringu.dtos.Aula;

public record AulasPendentesNotificacaoDTO(
        Integer alunoId,
        String nomeAluno,
        Integer personalId,
        String nomePersonal,
        Integer planoContratadoId,
        Long totalAulasPlano,
        Long aulasAgendadas,
        Long aulasDisponiveis,
        String mensagemAluno,
        String mensagemPersonal
) {
    // Construtor com 7 parâmetros (usado pela query)
    public AulasPendentesNotificacaoDTO(
            Integer alunoId,
            String nomeAluno,
            Integer personalId,
            String nomePersonal,
            Integer planoContratadoId,
            Long totalAulasPlano,
            Long aulasAgendadas
    ) {
        this(
                alunoId,
                nomeAluno,
                personalId,
                nomePersonal,
                planoContratadoId,
                totalAulasPlano,
                aulasAgendadas,
                totalAulasPlano - aulasAgendadas, // Calcula aulas disponíveis
                construirMensagemAluno(totalAulasPlano - aulasAgendadas, nomePersonal),
                construirMensagemPersonal(totalAulasPlano - aulasAgendadas, nomeAluno)
        );
    }

    // Construtor com 8 parâmetros (para testes ou outros usos)
    public AulasPendentesNotificacaoDTO(
            Integer alunoId,
            String nomeAluno,
            Integer personalId,
            String nomePersonal,
            Integer planoContratadoId,
            Long totalAulasPlano,
            Long aulasAgendadas,
            Long aulasDisponiveis
    ) {
        this(
                alunoId,
                nomeAluno,
                personalId,
                nomePersonal,
                planoContratadoId,
                totalAulasPlano,
                aulasAgendadas,
                aulasDisponiveis,
                construirMensagemAluno(aulasDisponiveis, nomePersonal),
                construirMensagemPersonal(aulasDisponiveis, nomeAluno)
        );
    }

    private static String construirMensagemAluno(Long aulasDisponiveis, String nomePersonal) {
        if (aulasDisponiveis == null || aulasDisponiveis <= 0) {
            return String.format("Todas as suas aulas com %s já foram agendadas!", nomePersonal);
        } else if (aulasDisponiveis == 1) {
            return String.format("Você ainda tem 1 aula disponível para agendar com %s!", nomePersonal);
        } else {
            return String.format("Você ainda tem %d aulas disponíveis para agendar com %s!",
                    aulasDisponiveis, nomePersonal);
        }
    }

    private static String construirMensagemPersonal(Long aulasDisponiveis, String nomeAluno) {
        if (aulasDisponiveis == null || aulasDisponiveis <= 0) {
            return String.format("Todas as aulas com %s já foram agendadas!", nomeAluno);
        } else if (aulasDisponiveis == 1) {
            return String.format("Você ainda tem 1 aula disponível para agendar com %s!", nomeAluno);
        } else {
            return String.format("Você ainda tem %d aulas disponíveis para agendar com %s!",
                    aulasDisponiveis, nomeAluno);
        }
    }
}