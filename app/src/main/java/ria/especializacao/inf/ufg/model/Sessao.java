package ria.especializacao.inf.ufg.model;

/**
 * Created by danillo on 31/03/2015.
 */
public class Sessao {

    private String data;
    private String horaInicial;
    private String horaFinal;
    private int qtdAcertos;
    private int qtdQuestoes;
    private int acertosEngSoftware;
    private int acertosDesenvolvimento;
    private int acertosRedes;
    private int acertosGovernanca;

    public Sessao() {
    }

    public Sessao(String data, String horaInicial, String horaFinal, int qtdAcertos, int qtdQuestoes, int acertosEngSoftware, int acertosDesenvolvimento, int acertosRedes, int acertosGovernanca) {
        this.data = data;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.qtdAcertos = qtdAcertos;
        this.qtdQuestoes = qtdQuestoes;
        this.acertosEngSoftware = acertosEngSoftware;
        this.acertosDesenvolvimento = acertosDesenvolvimento;
        this.acertosRedes = acertosRedes;
        this.acertosGovernanca = acertosGovernanca;
    }

    public int getAcertosDesenvolvimento() {
        return acertosDesenvolvimento;
    }

    public void setAcertosDesenvolvimento(int acertosDesenvolvimento) {
        this.acertosDesenvolvimento = acertosDesenvolvimento;
    }

    public int getAcertosEngSoftware() {
        return acertosEngSoftware;
    }

    public void setAcertosEngSoftware(int acertosEngSoftware) {
        this.acertosEngSoftware = acertosEngSoftware;
    }

    public int getAcertosRedes() {
        return acertosRedes;
    }

    public void setAcertosRedes(int acertosRedes) {
        this.acertosRedes = acertosRedes;
    }

    public int getAcertosGovernanca() {
        return acertosGovernanca;
    }

    public void setAcertosGovernanca(int acertosGovernanca) {
        this.acertosGovernanca = acertosGovernanca;
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
