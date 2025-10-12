package tech.vitalis.caringu.dtos;

public class ProgressoAulasDTO {
    private Long alunoId;
    private Long totalAulas;
    private Long aulasRealizadas;
    private Long aulasPendentes;
    private Double percentualConclusao;

    public ProgressoAulasDTO() {
    }

    public ProgressoAulasDTO(Long alunoId, Long totalAulas, Long aulasRealizadas) {
        this.alunoId = alunoId;
        this.totalAulas = totalAulas;
        this.aulasRealizadas = aulasRealizadas;
        this.aulasPendentes = totalAulas - aulasRealizadas;
        this.percentualConclusao = totalAulas > 0 ? (aulasRealizadas * 100.0) / totalAulas : 0.0;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getTotalAulas() {
        return totalAulas;
    }

    public void setTotalAulas(Long totalAulas) {
        this.totalAulas = totalAulas;
    }

    public Long getAulasRealizadas() {
        return aulasRealizadas;
    }

    public void setAulasRealizadas(Long aulasRealizadas) {
        this.aulasRealizadas = aulasRealizadas;
    }

    public Long getAulasPendentes() {
        return aulasPendentes;
    }

    public void setAulasPendentes(Long aulasPendentes) {
        this.aulasPendentes = aulasPendentes;
    }

    public Double getPercentualConclusao() {
        return percentualConclusao;
    }

    public void setPercentualConclusao(Double percentualConclusao) {
        this.percentualConclusao = percentualConclusao;
    }
}

