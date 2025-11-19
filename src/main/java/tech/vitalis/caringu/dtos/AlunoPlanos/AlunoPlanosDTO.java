package tech.vitalis.caringu.dtos.AlunoPlanos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class AlunoPlanosDTO {

    private Long planoContratadoId;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFim;

    private PlanoInfo plano;
    private PersonalInfo personalTrainer;
    private String locaisAtendimento;
    private AvaliacaoInfo avaliacao;

    public static class PlanoInfo {
        private String nome;
        private Integer quantidadeAulas;
        private Double valorAulas;

        public PlanoInfo(String nome, Integer quantidadeAulas, Double valorAulas) {
            this.nome = nome;
            this.quantidadeAulas = quantidadeAulas;
            this.valorAulas = valorAulas;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Integer getQuantidadeAulas() {
            return quantidadeAulas;
        }

        public void setQuantidadeAulas(Integer quantidadeAulas) {
            this.quantidadeAulas = quantidadeAulas;
        }

        public Double getValorAulas() {
            return valorAulas;
        }

        public void setValorAulas(Double valorAulas) {
            this.valorAulas = valorAulas;
        }
    }

    public static class AvaliacaoInfo {
        private Integer id;
        private Double nota;
        private String comentario;
        private String dataAvaliacao;

        public AvaliacaoInfo() {}

        public AvaliacaoInfo(Integer id, Double nota, String comentario, String dataAvaliacao) {
            this.id = id;
            this.nota = nota;
            this.comentario = comentario;
            this.dataAvaliacao = dataAvaliacao;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getNota() {
            return nota;
        }

        public void setNota(Double nota) {
            this.nota = nota;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public String getDataAvaliacao() {
            return dataAvaliacao;
        }

        public void setDataAvaliacao(String dataAvaliacao) {
            this.dataAvaliacao = dataAvaliacao;
        }
    }

    public static class PersonalInfo {
        private Integer id;
        private String nome;
        private String email;
        private String experiencia;

        public PersonalInfo(Integer id, String nome, String email, String experiencia) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.experiencia = experiencia;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getExperiencia() {
            return experiencia;
        }

        public void setExperiencia(String experiencia) {
            this.experiencia = experiencia;
        }
    }

    public AlunoPlanosDTO() {}

    public AlunoPlanosDTO(Long planoContratadoId, String status, LocalDate dataFim,
                          PlanoInfo plano, PersonalInfo personalTrainer,
                          String locaisAtendimento) {
        this.planoContratadoId = planoContratadoId;
        this.status = status;
        this.dataFim = dataFim;
        this.plano = plano;
        this.personalTrainer = personalTrainer;
        this.locaisAtendimento = locaisAtendimento;
    }

    public Long getPlanoContratadoId() {
        return planoContratadoId;
    }

    public void setPlanoContratadoId(Long planoContratadoId) {
        this.planoContratadoId = planoContratadoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public PlanoInfo getPlano() {
        return plano;
    }

    public void setPlano(PlanoInfo plano) {
        this.plano = plano;
    }

    public PersonalInfo getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalInfo personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public String getLocaisAtendimento() {
        return locaisAtendimento;
    }

    public void setLocaisAtendimento(String locaisAtendimento) {
        this.locaisAtendimento = locaisAtendimento;
    }

    public AvaliacaoInfo getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoInfo avaliacao) {
        this.avaliacao = avaliacao;
    }
}
