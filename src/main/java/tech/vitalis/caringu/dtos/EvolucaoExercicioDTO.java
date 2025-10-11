package tech.vitalis.caringu.dtos;

public class EvolucaoExercicioDTO {
    private Long exercicioId;
    private String nomeExercicio;
    private Double cargaAntiga;
    private Double cargaAtual;
    private Double evolucao;

    public EvolucaoExercicioDTO() {
    }

    public EvolucaoExercicioDTO(Long exercicioId, String nomeExercicio, Double cargaAntiga, Double cargaAtual) {
        this.exercicioId = exercicioId;
        this.nomeExercicio = nomeExercicio;
        this.cargaAntiga = cargaAntiga;
        this.cargaAtual = cargaAtual;
        this.evolucao = cargaAtual - cargaAntiga;
    }

    public Long getExercicioId() {
        return exercicioId;
    }

    public void setExercicioId(Long exercicioId) {
        this.exercicioId = exercicioId;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public Double getCargaAntiga() {
        return cargaAntiga;
    }

    public void setCargaAntiga(Double cargaAntiga) {
        this.cargaAntiga = cargaAntiga;
    }

    public Double getCargaAtual() {
        return cargaAtual;
    }

    public void setCargaAtual(Double cargaAtual) {
        this.cargaAtual = cargaAtual;
    }

    public Double getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(Double evolucao) {
        this.evolucao = evolucao;
    }
}

