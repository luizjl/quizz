package ria.especializacao.inf.br.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ria.especializacao.inf.br.model.Registro;
import ria.especializacao.inf.br.model.Sessao;

/**
 * Created by danillo on 03/04/2015.
 */
public class RegistroDAO {

    private SQLiteDatabase bd;
    private QuizBD quizBD;
    private Context contexto;


    public RegistroDAO( Context contexto) {
        this.contexto = contexto;
        quizBD = new QuizBD(this.contexto);
    }

    public long inserir( Registro registro){
        bd =  quizBD.getWritableDatabase();
        ContentValues valores =  new ContentValues();
        valores.put("data",registro.getIdPergunta());
        return  bd.insert("registro",null,valores);
    }

    public long remonerAll(){
        bd = quizBD.getWritableDatabase();
        return bd.delete("registro",null,null);
    }


}
