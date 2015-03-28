package ria.especializacao.inf.ufg.quizz.model;

/**
 * Created by danillo on 28/03/2015.
 */
public class Questoes {
    private String nome;
    private String teste;

    public Questoes() {

    }

    public Questoes(String nome, String teste) {
        this.nome = nome;
        this.teste = teste;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }
}

