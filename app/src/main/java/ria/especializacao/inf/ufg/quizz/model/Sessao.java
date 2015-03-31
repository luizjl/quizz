package ria.especializacao.inf.ufg.quizz.model;

/**
 * Created by danillo on 31/03/2015.
 */
public class Sessao {

    private String data;
    private String horaInicial;
    private String horaFinal;
    private int qtdAcertos;
    private int qtdQuestoes;

    public Sessao() {
    }

    public Sessao(String data, String horaInicial, String horaFinal, int qtdAcertos, int qtdQuestoes) {
        this.data = data;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.qtdAcertos = qtdAcertos;
        this.qtdQuestoes = qtdQuestoes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public int getQtdAcertos() {
        return qtdAcertos;
    }

    public void setQtdAcertos(int qtdAcertos) {
        this.qtdAcertos = qtdAcertos;
    }

    public int getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdQuestoes(int qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
    }
}
