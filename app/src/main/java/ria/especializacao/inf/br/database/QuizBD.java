package ria.especializacao.inf.br.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by danillo on 31/03/2015.
 */
public class QuizBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Quiz";
    private static final String DATATABLE_NAME = "sessao";
    private static final String DATATABLE_NAME_2 = "registro";
    private static final String DATATABLE_NAME_SUGESTOES = "sugestoes";
    private static final int DATABASE_VERSION = 20;



    /**
     * Queries
     * */
    private static final String DATABASE_CREATE_SUGESTOES =
            "create table if not exists "+DATATABLE_NAME_SUGESTOES+"( _id integer primary key autoincrement, ano integer, categoria text, " +
                    "cargo text, instituicao text, orgao text, cabecalho text, alternativa1 text, alternativa2 text," +
                    " alternativa3 text, alternativa4 text, alternativa5 text, resposta text);";

     private static final String DATABASE_CREATE_SESSAO =
            "CREATE TABLE  if not exists "+DATATABLE_NAME+"("+
                    "id integer PRIMARY KEY AUTOINCREMENT ," +
                    "qtdAcertos integer," +
                    "qtdErros integer)";


    private  static final String DATABASE_CREATE_REGISTRO = "" +
            "CREATE TABLE if not exists "+DATATABLE_NAME_2+"("+
            "idPergunta integer )";



    public QuizBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SUGESTOES);
        db.execSQL(DATABASE_CREATE_SESSAO);
        db.execSQL(DATABASE_CREATE_REGISTRO);
        db.execSQL("INSERT INTO sessao (qtdAcertos, qtdErros) VALUES (0, 0)");
        Log.v("Sqlite","Criando de tabelas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATATABLE_NAME_SUGESTOES);
        db.execSQL("DROP TABLE IF EXISTS " + DATATABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DATATABLE_NAME_2);
        onCreate(db);
    }
}
