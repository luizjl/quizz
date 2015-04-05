package ria.especializacao.inf.br.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danillo on 31/03/2015.
 */
public class QuizBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Quiz";
    private static final String DATATABLE_NAME = "sessao";
    private static final String DATATABLE_NAME_2 = "registro";
    private static final int DATABASE_VERSION = 13;



    /**
     * Queries
     * */
    private static final String DATABASE_CREATE_SESSAO =
            "CREATE TABLE  if not exists "+DATATABLE_NAME+"("+
                    "id integer PRIMARY KEY AUTOINCREMENT ," +
                    "qtdAcertos integer," +
                    "qtdErros integer)";


    private  static final String DATABASE_CREATE_REGISTRO = "" +
            "CREATE TABLE if not exist "+DATATABLE_NAME_2+"("+
            "idPergunta integer )";



    public QuizBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SESSAO);
        //db.execSQL(DATABASE_CREATE_REGISTRO);
        db.execSQL("INSERT INTO sessao (qtdAcertos, qtdErros) VALUES ('0', '0')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATATABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DATATABLE_NAME_2);
        onCreate(db);
    }
}
