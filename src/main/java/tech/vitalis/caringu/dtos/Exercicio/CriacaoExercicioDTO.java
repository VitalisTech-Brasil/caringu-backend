package tech.vitalis.caringu.dtos.Exercicio;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriacaoExercicioDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O grupo muscular é obrigatório")
    private String grupoMuscular;

    private Boolean favorito;

    private String origem;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public String toString() {
        return "CriacaoExercicioDTO{" +
                "nome='" + nome + '\'' +
                ", grupoMuscular='" + grupoMuscular + '\'' +
                ", favorito=" + favorito +
                ", origem='" + origem + '\'' +
                '}';
    }
}
