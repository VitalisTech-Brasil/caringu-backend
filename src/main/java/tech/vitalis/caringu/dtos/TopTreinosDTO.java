package tech.vitalis.caringu.dtos;

public class TopTreinosDTO {
    private Long alunosId;
    private Long treinoId;
    private String treinoNome;
    private Long qtdVezesRealizado;

    public TopTreinosDTO() {
    }

    public TopTreinosDTO(Long alunosId, Long treinoId, String treinoNome, Long qtdVezesRealizado) {
        this.alunosId = alunosId;
        this.treinoId = treinoId;
        this.treinoNome = treinoNome;
        this.qtdVezesRealizado = qtdVezesRealizado;
    }

    public Long getAlunosId() {
        return alunosId;
    }

    public void setAlunosId(Long alunosId) {
        this.alunosId = alunosId;
    }

    public Long getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(Long treinoId) {
        this.treinoId = treinoId;
    }

    public String getTreinoNome() {
        return treinoNome;
    }

    public void setTreinoNome(String treinoNome) {
        this.treinoNome = treinoNome;
    }

    public Long getQtdVezesRealizado() {
        return qtdVezesRealizado;
    }

    public void setQtdVezesRealizado(Long qtdVezesRealizado) {
        this.qtdVezesRealizado = qtdVezesRealizado;
    }
}

