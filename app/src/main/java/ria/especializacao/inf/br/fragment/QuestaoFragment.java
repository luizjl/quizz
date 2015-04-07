package ria.especializacao.inf.br.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.model.Questoes;
import ria.especializacao.inf.br.model.Sessao;
import ria.especializacao.inf.br.quiz.QuestaoActivity;
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
    private int cont = 1;
    private Button button;
    private Integer[] listaAux;
    private int indexAux;

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

        final Button proximaQuestao = (Button) rootView.findViewById(R.id.proximaQuestaoButton);
        proximaQuestao.setVisibility(View.INVISIBLE);

        int idCategoria = getActivity().getIntent().getIntExtra("idCategoria", 0);
        //Toast.makeText(getActivity(), "Você Clicou" + idCategoria, Toast.LENGTH_SHORT).show();

        cabecalhoQuestao = (TextView) rootView.findViewById(R.id.idCabecalho);
        alternativaQuestao = (RadioGroup) rootView.findViewById(R.id.idAlternativaQuestao);

        FetchQuestaoTask fetchQuestaoTask = new FetchQuestaoTask();
        fetchQuestaoTask.execute(idCategoria);

        button = (Button) rootView.findViewById(R.id.respostaButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // PEGAR ID DO RÁDIO SELECIOMNADO
                int radioButtonID = alternativaQuestao.getCheckedRadioButtonId();
                View radioButton = alternativaQuestao.findViewById(radioButtonID);

                if(radioButton != null)
                {
                    int idx = alternativaQuestao.indexOfChild(radioButton);
                    //Toast.makeText(getActivity(), String.valueOf(validarQuestao(idx, questoesList.get(indiceList).getResposta())), Toast.LENGTH_SHORT).show();
                    boolean resultado = validarQuestao(idx, questoesList.get(indiceList).getResposta());

                    if(resultado)
                    {
                        //Toast.makeText(getActivity(), "Sua Resposta Está Correta", Toast.LENGTH_LONG).show();
                        //radioButton.setBackgroundColor(Color.GREEN);
                        Crouton.makeText(getActivity(), "Sua Resposta Está Correta", Style.INFO).show();
                        button.setVisibility(View.INVISIBLE);
                        proximaQuestao.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        //Toast.makeText(getActivity(), "Sua Resposta Está Incorreta", Toast.LENGTH_LONG).show();
                        Crouton.makeText(getActivity(), "Sua Resposta Está Incorreta", Style.ALERT).show();
                        //radioButton.setBackgroundColor(Color.RED);
                    }
                }
                else
                {
                    //Toast.makeText(getActivity(), "Selecione Uma Das Opções!!", Toast.LENGTH_SHORT).show();
                    Crouton.makeText(getActivity(), "Selecione Uma Das Opções!!", Style.ALERT).show();
                }

            }
        });

        proximaQuestao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cont < questoesList.size())
                {

                    //int index = randomList(listaAux.length);

                    cabecalhoQuestao.setText(questoesList.get(listaAux[cont]).getCabecalho());

                    ActionBar actionBar = ((QuestaoActivity) getActivity()).getSupportActionBar();
                    String titulo = questoesList.get(listaAux[cont]).getInstituicao()+" ["+questoesList.get(listaAux[cont]).getOrgao()+"]";
                    actionBar.setTitle(titulo);

                    for (int i = 0; i < alternativaQuestao.getChildCount(); i++)
                    {
                        ((RadioButton) alternativaQuestao.getChildAt(i)).setText(questoesList.get(listaAux[cont]).getAlternativas().get(i));
                    }

                    cont++;

                    button.setVisibility(View.VISIBLE);
                    proximaQuestao.setVisibility(View.INVISIBLE);

                    // LIMPANDO CHECKBOX E UNCHEKING
                    int radioButtonID = alternativaQuestao.getCheckedRadioButtonId();
                    View radioButton = alternativaQuestao.findViewById(radioButtonID);
                    alternativaQuestao.clearCheck();
                    radioButton.setBackgroundColor(Color.TRANSPARENT);
                }
                else
                {
                    cabecalhoQuestao.setText("Não Há Mais Perguntas Para Essa Categoria!");

                    alternativaQuestao.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    proximaQuestao.setVisibility(View.INVISIBLE);

                    ActionBar actionBar = ((QuestaoActivity) getActivity()).getSupportActionBar();
                    String titulo = "FIM";
                    actionBar.setTitle(titulo);
                }
            }
        });

        return  rootView;
    }

    public Boolean validarQuestao(int idx, int resposta)
    {
        int acertos = sessaoDAO.getSessoes().getQtdAcertos();
        int erros = sessaoDAO.getSessoes().getQtdErros();

        if(idx == resposta)
        {
            //int acertos = sessaoDAO.getSessoes().getQtdAcertos();
            //Log.v("Pegando Valores Banco:", String.valueOf(sessao.getQtdAcertos())+" Variavel Acertos:"+acertos);
            acertos++;
            sessao.setQtdAcertos(acertos);
            sessao.setQtdErros(erros);
            sessaoDAO.atualizar(sessao);
            sessao =  sessaoDAO.getSessoes();
            //Log.v("Sessao", String.valueOf(acertos));
            return true;
        }
        else
        {
            //int erros = sessao.getQtdErros();
            //Log.v("Pegando Valores Banco:", String.valueOf(sessao.getQtdErros())+" Variavel Erros:"+erros);
            erros++;
            sessao.setQtdAcertos(acertos);
            sessao.setQtdErros(erros);
            sessaoDAO.atualizar(sessao);
            sessao =  sessaoDAO.getSessoes();
            Log.v("Sessao", String.valueOf(erros));
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
                //Log.v("TAMANHO", String.valueOf(result.size()));

                listaAux = new Integer[result.size()];

                for(int j = 0; j < listaAux.length; j++)
                {
                    listaAux[j] = j;
                    //Log.v("ANTES", String.valueOf(listaAux[j]));
                }

                Collections.shuffle(Arrays.asList(listaAux));

                /*for(int j = 0; j < listaAux.length; j++)
                {
                    Log.v("DEPOIS", String.valueOf(listaAux[j]));
                }*/

                //int index = randomList(listaAux.length);

                //Log.v("RANDOM", String.valueOf(listaAux[0]));

                ActionBar actionBar = ((QuestaoActivity) getActivity()).getSupportActionBar();
                String titulo = result.get(listaAux[0]).getInstituicao()+" ["+result.get(listaAux[0]).getOrgao()+"]";
                actionBar.setTitle(titulo);
                //Log.v("ORGAO", result.get(0).getOrgao());

                cabecalhoQuestao.setText(result.get(listaAux[0]).getCabecalho());

                for(int i = 0; i < alternativaQuestao.getChildCount(); i++)
                {
                    ((RadioButton) alternativaQuestao.getChildAt(i)).setText(result.get(listaAux[0]).getAlternativas().get(i));
                }

                questoesList = result;
            }
            else
            {
                alternativaQuestao.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                cabecalhoQuestao.setText("Não Há Questões Cadastradas :(");
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

    /*public int randomList(int listaSize)
    {
        return  (int)(Math.random()*listaSize);
    }*/
}