package ria.especializacao.inf.ufg.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.ufg.model.Sessao;

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
        return  bd.insert("sessao",null,valores);
    }

    public List<Sessao> getSessoes(){

        bd = quizBD.getReadableDatabase();

        List<Sessao> listaSessao = new ArrayList<Sessao>();
        String[] colunas  = {"data","horaInicial","horaFinal","qtdAcertos","qtdQuestoes"};

        Cursor cursor = bd.query("sessao",colunas,null,null,null,null,null);

        if ( cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Sessao sessao = new Sessao();
                sessao.setData(cursor.getString(0));
                sessao.setHoraInicial(cursor.getString(1));
                sessao.setHoraFinal(cursor.getString(2));
                sessao.setQtdAcertos(cursor.getInt(3));
                sessao.setQtdQuestoes(cursor.getInt(4));
                listaSessao.add(sessao);

            }while (cursor.moveToNext());
        }

        return listaSessao;
    }



}
