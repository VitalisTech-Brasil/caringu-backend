package tech.vitalis.caringu.infrastructure.persistence.anamnese;

import jakarta.persistence.*;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.core.domain.valueObject.FrequenciaTreinoEnum;
import tech.vitalis.caringu.infrastructure.persistence.converter.FrequenciaTreinoEnumConverter;

@Entity(name = "Anamnese")
@Table(name = "anamnese", schema = "vitalis")
public class AnamneseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_id", referencedColumnName = "id", unique = true)
    private Aluno aluno;

    @Column(name = "objetivo_treino", nullable = false, columnDefinition = "TEXT")
    private String objetivoTreino;

    @Column(nullable = false)
    private Boolean lesao;

    @Column(name = "lesao_descricao", columnDefinition = "TEXT")
    private String lesaoDescricao;

    @Convert(converter = FrequenciaTreinoEnumConverter.class)
    @Column(name = "frequencia_treino", nullable = false)
    private FrequenciaTreinoEnum frequenciaTreino;

    @Column(nullable = false)
    private Boolean experiencia;

    @Column(name = "experiencia_descricao", columnDefinition = "TEXT")
    private String experienciaDescricao;

    @Column(nullable = false)
    private Boolean desconforto;

    @Column(name = "desconforto_descricao", columnDefinition = "TEXT")
    private String desconfortoDescricao;

    @Column(nullable = false)
    private Boolean fumante;

    @Column(nullable = false)
    private Boolean proteses;

    @Column(name = "proteses_descricao", columnDefinition = "TEXT")
    private String protesesDescricao;

    @Column(name = "doenca_metabolica", nullable = false)
    private Boolean doencaMetabolica;

    @Column(name = "doenca_metabolica_descricao", columnDefinition = "TEXT")
    private String doencaMetabolicaDescricao;

    @Column(nullable = false)
    private Boolean deficiencia;

    @Column(name = "deficiencia_descricao", columnDefinition = "TEXT")
    private String deficienciaDescricao;

    public AnamneseEntity(Aluno aluno, String objetivoTreino, Boolean lesao, String lesaoDescricao, FrequenciaTreinoEnum frequenciaTreino, Boolean experiencia, String experienciaDescricao, Boolean desconforto, String desconfortoDescricao, Boolean fumante, Boolean proteses, String protesesDescricao, Boolean doencaMetabolica, String doencaMetabolicaDescricao, Boolean deficiencia, String deficienciaDescricao) {
        this.aluno = aluno;
        this.objetivoTreino = objetivoTreino;
        this.lesao = lesao;
        this.lesaoDescricao = lesaoDescricao;
        this.frequenciaTreino = frequenciaTreino;
        this.experiencia = experiencia;
        this.experienciaDescricao = experienciaDescricao;
        this.desconforto = desconforto;
        this.desconfortoDescricao = desconfortoDescricao;
        this.fumante = fumante;
        this.proteses = proteses;
        this.protesesDescricao = protesesDescricao;
        this.doencaMetabolica = doencaMetabolica;
        this.doencaMetabolicaDescricao = doencaMetabolicaDescricao;
        this.deficiencia = deficiencia;
        this.deficienciaDescricao = deficienciaDescricao;
    }

    public AnamneseEntity(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getObjetivoTreino() {
        return objetivoTreino;
    }

    public void setObjetivoTreino(String objetivoTreino) {
        this.objetivoTreino = objetivoTreino;
    }

    public Boolean getLesao() {
        return lesao;
    }

    public void setLesao(Boolean lesao) {
        this.lesao = lesao;
    }

    public String getLesaoDescricao() {
        return lesaoDescricao;
    }

    public void setLesaoDescricao(String lesaoDescricao) {
        this.lesaoDescricao = lesaoDescricao;
    }

    public FrequenciaTreinoEnum getFrequenciaTreino() {
        return frequenciaTreino;
    }

    public void setFrequenciaTreino(FrequenciaTreinoEnum frequenciaTreino) {
        this.frequenciaTreino = frequenciaTreino;
    }

    public Boolean getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Boolean experiencia) {
        this.experiencia = experiencia;
    }

    public String getExperienciaDescricao() {
        return experienciaDescricao;
    }

    public void setExperienciaDescricao(String experienciaDescricao) {
        this.experienciaDescricao = experienciaDescricao;
    }

    public Boolean getDesconforto() {
        return desconforto;
    }

    public void setDesconforto(Boolean desconforto) {
        this.desconforto = desconforto;
    }

    public String getDesconfortoDescricao() {
        return desconfortoDescricao;
    }

    public void setDesconfortoDescricao(String desconfortoDescricao) {
        this.desconfortoDescricao = desconfortoDescricao;
    }

    public Boolean getFumante() {
        return fumante;
    }

    public void setFumante(Boolean fumante) {
        this.fumante = fumante;
    }

    public Boolean getProteses() {
        return proteses;
    }

    public void setProteses(Boolean proteses) {
        this.proteses = proteses;
    }

    public String getProtesesDescricao() {
        return protesesDescricao;
    }

    public void setProtesesDescricao(String protesesDescricao) {
        this.protesesDescricao = protesesDescricao;
    }

    public Boolean getDoencaMetabolica() {
        return doencaMetabolica;
    }

    public void setDoencaMetabolica(Boolean doencaMetabolica) {
        this.doencaMetabolica = doencaMetabolica;
    }

    public String getDoencaMetabolicaDescricao() {
        return doencaMetabolicaDescricao;
    }

    public void setDoencaMetabolicaDescricao(String doencaMetabolicaDescricao) {
        this.doencaMetabolicaDescricao = doencaMetabolicaDescricao;
    }

    public Boolean getDeficiencia() {
        return deficiencia;
    }

    public void setDeficiencia(Boolean deficiencia) {
        this.deficiencia = deficiencia;
    }

    public String getDeficienciaDescricao() {
        return deficienciaDescricao;
    }

    public void setDeficienciaDescricao(String deficienciaDescricao) {
        this.deficienciaDescricao = deficienciaDescricao;
    }
}
