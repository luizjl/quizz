package ria.especializacao.inf.br.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public long atualizar( Sessao sessao ){
        bd =  quizBD.getWritableDatabase();
        ContentValues valores =  new ContentValues();
        valores.put("qtdAcertos", sessao.getQtdAcertos());
        valores.put("qtdErros", sessao.getQtdErros());

        String[] args = {"1"} ;

        return  bd.update("sessao",valores,"id=?",args);
    }

    public Sessao getSessoes(){

        bd = quizBD.getReadableDatabase();

        Sessao sessao = new Sessao();

        String[] colunas  = {"qtdAcertos","qtdErros"};

        Cursor cursor = bd.query("sessao", colunas, null, null, null, null, null);


        if ( cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            sessao.setQtdAcertos(cursor.getInt(0));
            sessao.setQtdErros(cursor.getInt(1));
            return sessao;
        }
        else
        {
            return null;
        }
    }
}
