package ria.especializacao.inf.br.model;

import java.util.List;

/**
 * Created by danillo on 28/03/2015.
 */
public class Questoes {
    private int id;
    private int ano;
    private String categoria;
    private String cargo;
    private String instituicao;
    private String orgao;
    private String cabecalho;
    private List<String> alternativas;
    private int resposta;


    public Questoes(){
        
    }

    public Questoes(int id, int ano, String categoria, String cargo, String instituicao, String orgao, String cabecalho, List<String> alternativas, int resposta) {
        this.id = id;
        this.ano = ano;
        this.categoria = categoria;
        this.cargo = cargo;
        this.instituicao = instituicao;
        this.orgao = orgao;
        this.cabecalho = cabecalho;
        this.alternativas = alternativas;
        this.resposta = resposta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<String> alternativas) {
        this.alternativas = alternativas;
    }

    public int getResposta() {
        return resposta;
    }

    public void setResposta(int resposta) {
        this.resposta = resposta;
    }
}

