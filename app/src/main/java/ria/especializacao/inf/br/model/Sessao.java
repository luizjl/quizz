package ria.especializacao.inf.br.model;

/**
 * Created by danillo on 31/03/2015.
 */
public class Sessao {

    private int qtdAcertos;
    private int qtdErros;

    public Sessao() {
    }

    public Sessao(int qtdErros, int qtdAcertos) {
        this.qtdErros = qtdErros;
        this.qtdAcertos = qtdAcertos;
    }

    public int getQtdAcertos() {
        return qtdAcertos;
    }

    public void setQtdAcertos(int qtdAcertos) {
        this.qtdAcertos = qtdAcertos;
    }

    public int getQtdErros() {
        return qtdErros;
    }

    public void setQtdErros(int qtdErros) {
        this.qtdErros = qtdErros;
    }
}
