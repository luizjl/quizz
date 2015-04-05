package ria.especializacao.inf.br.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;
import java.util.Random;

import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.model.Questoes;
import ria.especializacao.inf.br.model.Sessao;
import ria.especializacao.inf.br.utils.ParserJson;
import ria.especializacao.inf.br.quiz.R;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link android.widget.ListView} layout.
 */
public class QuestaoFragment extends android.support.v4.app.Fragment
{
    private TextView cabecalhoQuestao;
    private RadioGroup alternativaQuestao;
    private List<Questoes> questoesList;
    private Integer indiceList = 0;
    private Sessao sessao;
    private SessaoDAO sessaoDAO;

    public QuestaoFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        sessaoDAO = new SessaoDAO(getActivity());
        sessao = new Sessao();
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
        final View rootView = inflater.inflate(R.layout.fragment_questao, container, false);

        int idCategoria = getActivity().getIntent().getIntExtra("idCategoria", 0);
        //Toast.makeText(getActivity(), "Você Clicou" + idCategoria, Toast.LENGTH_SHORT).show();

        cabecalhoQuestao = (TextView) rootView.findViewById(R.id.idCabecalho);
        alternativaQuestao = (RadioGroup) rootView.findViewById(R.id.idAlternativaQuestao);

        FetchQuestaoTask fetchQuestaoTask = new FetchQuestaoTask();
        fetchQuestaoTask.execute(idCategoria);

        Button button = (Button) rootView.findViewById(R.id.respostaButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // PEGAR ID DO RÁDIO SELECIOMNADO
                int radioButtonID = alternativaQuestao.getCheckedRadioButtonId();
                View radioButton = alternativaQuestao.findViewById(radioButtonID);
                int idx = alternativaQuestao.indexOfChild(radioButton);

                //Toast.makeText(getActivity(), String.valueOf(validarQuestao(idx, questoesList.get(indiceList).getResposta())), Toast.LENGTH_SHORT).show();
                boolean resultado = validarQuestao(idx, questoesList.get(indiceList).getResposta());

            }
        });

        return  rootView;
    }

    public Boolean validarQuestao(int idx, int resposta)
    {
            if(idx == resposta)
            {
                int acertos = sessaoDAO.getSessoes().getQtdAcertos();
                Log.v("Pegando Valores Banco:", String.valueOf(sessao.getQtdAcertos())+" Variavel Acertos:"+acertos);
                acertos++;
                sessao.setQtdAcertos(acertos);
                sessao.setQtdErros(3);
                sessaoDAO.atualizar(sessao);
                sessao =  sessaoDAO.getSessoes();
                Log.v("Sessao", String.valueOf(acertos));
                return true;
            }
            else
            {
                int erros = sessao.getQtdErros();
                erros++;
                sessao.setQtdErros(erros);
                sessaoDAO.atualizar(sessao);
                return false;
            }

    }

    public class FetchQuestaoTask extends AsyncTask<Integer, Void, List<Questoes>>
    {
        @Override
        public void onPostExecute(List<Questoes> result)
        {
            if(!result.isEmpty())
            {
                cabecalhoQuestao.setText(result.get(0).getCabecalho());

                for(int i = 0; i < alternativaQuestao.getChildCount(); i++)
                {
                    ((RadioButton) alternativaQuestao.getChildAt(i)).setText(result.get(0).getAlternativas().get(i));
                }

                questoesList = result;

                /*for (int i = 0; i < result.size(); i++)
                {
                    cabecalhoQuestao.setText(result.get(i).getCabecalho());

                    for(int j = 0; j < alternativaQuestao.getChildCount(); j++)
                    {
                        ((RadioButton) alternativaQuestao.getChildAt(j)).setText(result.get(i).getAlternativas().get(j));
                    }

                } */
            }
            else
            {
                alternativaQuestao.setVisibility(View.INVISIBLE);
                cabecalhoQuestao.setText("Não Há Questões Cadastradas :(");
            }

            /*int i = 1;

            if(result != null)
            {
                cabecalhoQuestao.setText(result.get(i).getCabecalho());
            }
            else
            {
                cabecalhoQuestao.setText("Não Há Questões Cadastradas =(");
            }*/
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