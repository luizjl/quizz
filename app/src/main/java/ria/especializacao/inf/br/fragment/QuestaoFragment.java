package ria.especializacao.inf.br.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.br.model.Questoes;
import ria.especializacao.inf.br.utils.ParserJson;
import ria.especializacao.inf.ufg.quiz.R;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link android.widget.ListView} layout.
 */
public class QuestaoFragment extends android.support.v4.app.Fragment
{
    TextView cabecalhoQuestao;

    public QuestaoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_questao, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_questao, container, false);

        cabecalhoQuestao = (TextView) rootView.findViewById(R.id.idCabecalho);

                FetchQuestaoTask fetchQuestaoTask = new FetchQuestaoTask();
        fetchQuestaoTask.execute(1);

        return  rootView;
    }

    public class FetchQuestaoTask extends AsyncTask<Integer, Void, List<Questoes>>
    {
        @Override
        public void onPostExecute(List<Questoes> result)
        {
            int i = 1;

            if(result != null)
            {
                cabecalhoQuestao.setText(result.get(i).getCabecalho());
            }
        }

        @Override
        protected List<Questoes> doInBackground(Integer... params)
        {
            if(params[0] == null)
            {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            List<Questoes> questaoList = new ArrayList<Questoes>();

            try
            {
                final String QUIZ_BASE_URL = "http://www.conquiz.com.br/categorias/json/"+params[0];
                URL url = new URL(QUIZ_BASE_URL.toString());

                Log.v("RETORNO", "Categoria string: " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                ParserJson json = new ParserJson();

                questaoList = json.readJsonStream(inputStream);
            }
            catch (IOException ex)
            {
                Log.e("Error", "Erro: "+ex);
            }
            finally
            {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
            }

            return questaoList;
        }
    }
}