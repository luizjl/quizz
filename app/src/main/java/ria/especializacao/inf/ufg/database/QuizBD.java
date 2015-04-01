package ria.especializacao.inf.ufg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danillo on 31/03/2015.
 */
public class QuizBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Quiz";
    private static final int DATABASE_VERSION = 1;



    /**
     * Queries
     * */
    private static final String DATABASE_CREATE_SESSAO =
            "CREATE TABLE  if not exists sessao (" +
                    "_id integer PRIMARY KEY," +
                    "data text not null," +
                    "horaInicial text not null," +
                    "horaFinal text not null," +
                    "qtdAcertos integer," +
                    "qtdQuestoes integer)";



    public QuizBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SESSAO);
        //db.execSQL("insert into "+DATABASE_NAME+".sessao(data,horaInicial,horaFinal,qtdAcertos,qtdQuestoes) values('31/03/2015','21:30:00','21:35:00',6,10)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_SESSAO);
        onCreate(db);
    }
}
