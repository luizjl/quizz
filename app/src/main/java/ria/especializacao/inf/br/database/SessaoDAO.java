package ria.especializacao.inf.br.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.br.model.Sessao;

/**
 * Created by danillo on 31/03/2015.
 */
public class SessaoDAO {

    private  SQLiteDatabase bd;
    private  QuizBD quizBD;
    private Context contexto;

    //criando conexao com banco de dados
    public SessaoDAO( Context contexto ){
        this.contexto = contexto;
        quizBD = new QuizBD(this.contexto);
    }

    public long inserir( Sessao sessao ){
        bd =  quizBD.getWritableDatabase();
        ContentValues valores =  new ContentValues();
        valores.put("data",sessao.getData());
        valores.put("horaInicial",sessao.getHoraInicial());
        valores.put("horaFinal",sessao.getHoraFinal());
        valores.put("qtdAcertos",sessao.getQtdAcertos());
        valores.put("qtdQuestoes",sessao.getQtdQuestoes());
        valores.put("acertosEngSoftware",sessao.getAcertosEngSoftware());
        valores.put("acertosDesenvolvimento",sessao.getAcertosDesenvolvimento());
        valores.put("acertosRedes",sessao.getAcertosRedes());
        valores.put("acertosGovernanca",sessao.getAcertosGovernanca());
        return  bd.insert("sessao",null,valores);
    }

    public List<Sessao> getSessoes(){

        bd = quizBD.getReadableDatabase();

        List<Sessao> listaSessao = new ArrayList<Sessao>();

        String[] colunas  = {"data","horaInicial","horaFinal","qtdAcertos","qtdQuestoes"
        ,"acertosEngSoftware","acertosDesenvolvimento","acertosRedes","acertosGovernanca"};

        String order = "data,horaInicial desc";

        Cursor cursor = bd.query("sessao",colunas,null,null,null,null,order);


        if ( cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Sessao sessao = new Sessao();
                sessao.setData(cursor.getString(0));
                sessao.setHoraInicial(cursor.getString(1));
                sessao.setHoraFinal(cursor.getString(2));
                sessao.setQtdAcertos(cursor.getInt(3));
                sessao.setQtdQuestoes(cursor.getInt(4));
                sessao.setAcertosEngSoftware(cursor.getInt(5));
                sessao.setAcertosDesenvolvimento(cursor.getInt(6));
                sessao.setAcertosRedes(cursor.getInt(7));
                sessao.setAcertosGovernanca(cursor.getInt(8));
                listaSessao.add(sessao);

            }while (cursor.moveToNext());
        }

        return listaSessao;
    }



}