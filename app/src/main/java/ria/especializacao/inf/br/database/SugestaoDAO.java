package ria.especializacao.inf.br.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.br.model.Questoes;

/**
 * Created by luiz on 05/04/15.
 */
public class SugestaoDAO {

    private SQLiteDatabase banco;

    private QuizBD quizDB;

    public static final String TABLE_NAME_SUGESTOES = "sugestoes";

    public static final String ID = "_id";

    public static final String ANO = "ano";

    public static final String CATEGORIA = "categoria";

    public static final String CARGO = "cargo";

    public static final String INSIITUICAO = "instituicao";

    public static final String ORGAO = "orgao";

    public static final String CABECALHO = "cabecalho";

    public static final String ALTERNATIVA1 = "alternativa1";

    public static final String ALTERNATIVA2 = "alternativa2";

    public static final String ALTERNATIVA3 = "alternativa3";

    public static final String ALTERNATIVA4 = "alternativa4";

    public static final String ALTERNATIVA5 = "alternativa5";

    public static final String RESPOSTA = "resposta";

    private Questoes questao;

    public SugestaoDAO(Context context){

        quizDB = new QuizBD(context);
    }

    public void open() throws SQLException {

        banco = quizDB.getWritableDatabase();
    }

    public void close(){

        quizDB.close();
    }

    public Long adicionarQuestao(Questoes q) {

        int ano = q.getAno();
        String categoria = q.getCategoria();
        String cargo = q.getCargo();
        String instituicao = q.getInstituicao();
        String orgao = q.getOrgao();
        String cabecalho = q.getCabecalho();
        String alternativa1 = q.getAlternativas().get(0);
        String alternativa2 = q.getAlternativas().get(1);
        String alternativa3 = q.getAlternativas().get(2);
        String alternativa4 = q.getAlternativas().get(3);
        String alternativa5 = q.getAlternativas().get(4);
        int resposta = q.getResposta();

        ContentValues cVal = new ContentValues();
        cVal.put(ANO, ano);
        cVal.put(CATEGORIA, categoria);
        cVal.put(CARGO, cargo);
        cVal.put(INSIITUICAO, instituicao);
        cVal.put(ORGAO, orgao);
        cVal.put(CABECALHO, cabecalho);
        cVal.put(ALTERNATIVA1, alternativa1);
        cVal.put(ALTERNATIVA2, alternativa2);
        cVal.put(ALTERNATIVA3, alternativa3);
        cVal.put(ALTERNATIVA4, alternativa4);
        cVal.put(ALTERNATIVA5, alternativa5);
        cVal.put(RESPOSTA, resposta);

        // Insert values in database
        Long r = banco.insert( TABLE_NAME_SUGESTOES, null, cVal);
        this.close(); // Closing database connection
        return r;
    }

    public List<Questoes> getAllQuestoes() {

        List<Questoes> listaQuestoes = new ArrayList<Questoes>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME_SUGESTOES;

        Cursor cursor = banco.rawQuery ( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Questoes data = new Questoes();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setAno(Integer.parseInt(cursor.getString(1)));
                data.setCategoria(cursor.getString(2));
                data.setCargo(cursor.getString(3));
                data.setInstituicao(cursor.getString(4));
                data.setOrgao(cursor.getString(5));
                data.setCabecalho(cursor.getString(6));

                List<String> alt = new ArrayList<String>();
                alt.add(0, cursor.getString(7));
                alt.add(1, cursor.getString(8));
                alt.add(2, cursor.getString(9));
                alt.add(3, cursor.getString(10));
                alt.add(4, cursor.getString(11));

                data.setAlternativas(alt);
                data.setResposta(Integer.parseInt(cursor.getString(12)));

                // Adicionando Questões à lista
                listaQuestoes.add(data);
            } while (cursor.moveToNext());
        }

        // return lista
        return listaQuestoes;
    }

}
