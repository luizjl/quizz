package ria.especializacao.inf.br.quiz;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ria.especializacao.inf.br.database.SugestaoDAO;
import ria.especializacao.inf.br.model.Questoes;

public class EnviarQuestaoActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_questao);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("ConQuiz TI");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 191, 255)));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EnviarQuestaoFragment())
                    .commit();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enviar_questao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class EnviarQuestaoFragment extends Fragment {

        private SugestaoDAO ds;

        public EnviarQuestaoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            ds = new SugestaoDAO(getActivity());

            View rootView = inflater.inflate(R.layout.fragment_enviar_questao, container, false);

            Spinner spinner = (Spinner) rootView.findViewById(R.id.spResposta);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opcoes_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.spinner_item);
            spinner.setAdapter(adapter);

            Spinner spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
            ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.categorias_array, android.R.layout.simple_spinner_item);
            adapterCategoria.setDropDownViewResource(R.layout.spinner_item);
            spCategoria.setAdapter(adapterCategoria);

            Button btSalvar = (Button) rootView.findViewById(R.id.btSalvar);
            btSalvar.setOnClickListener(new Button.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                public void onClick(View v) {

                    ds.open();

                    EditText etAno = (EditText) getActivity().findViewById(R.id.etAno);

                    Spinner spCategoria = (Spinner) getActivity().findViewById(R.id.spCategoria);
                    String strCategoria = (String) spCategoria.getSelectedItem();

                    EditText etCargo = (EditText) getActivity().findViewById(R.id.etCargo);

                    EditText etInstituicao = (EditText) getActivity().findViewById(R.id.etInstituicao);

                    EditText etOrgao = (EditText) getActivity().findViewById(R.id.etOrgao);

                    EditText etQuestao = (EditText) getActivity().findViewById(R.id.etQuestao);

                    EditText etAlternativa1 = (EditText) getActivity().findViewById(R.id.etAlternativa1);

                    EditText etAlternativa2 = (EditText) getActivity().findViewById(R.id.etAlternativa2);

                    EditText etAlternativa3 = (EditText) getActivity().findViewById(R.id.etAlternativa3);

                    EditText etAlternativa4 = (EditText) getActivity().findViewById(R.id.etAlternativa4);

                    EditText etAlternativa5 = (EditText) getActivity().findViewById(R.id.etAlternativa5);

                    Spinner spResposta = (Spinner) getActivity().findViewById(R.id.spResposta);

                    if(etAno == null || etAno.getText().toString().equals("")) {
                        mostrarErro("Por favor, digite o ANO!");
                        etAno.requestFocus();
                    }
                    else if(strCategoria == null || strCategoria.equals("")) {
                        mostrarErro("Selecione uma Categoria");
                    }
                    else if(etCargo == null || etCargo.getText().toString().equals("")) {
                        mostrarErro("Insira o Cargo!");
                        etCargo.requestFocus();
                    }
                    else if(etInstituicao == null || etInstituicao.getText().toString().equals("")) {
                        mostrarErro("De qual Institução?");
                        etInstituicao.requestFocus();
                    }
                    else if(etOrgao == null || etOrgao.getText().toString().equals("")) {
                        mostrarErro("Prova de qual Órgão?");
                        etOrgao.requestFocus();
                    }
                    else if(etQuestao == null || etQuestao.getText().toString().equals("")) {
                        mostrarErro("Faltou a Questão!");
                        etQuestao.requestFocus();
                    }
                    else if(etAlternativa1 == null || etAlternativa1.getText().toString().equals("")) {
                        mostrarErro("Qual a primeira alternativa?");
                        etAlternativa1.requestFocus();
                    }
                    else if(etAlternativa2 == null || etAlternativa2.getText().toString().equals("")) {
                        mostrarErro("Digite a segunda alternativa!");
                        etAlternativa2.requestFocus();
                    }
                    else if(etAlternativa3 == null || etAlternativa3.getText().toString().equals("")) {
                        mostrarErro("Preencha a terceira alternativa!");
                        etAlternativa3.requestFocus();
                    }
                    else if(etAlternativa4 == null || etAlternativa4.getText().toString().equals("")) {
                        mostrarErro("Qual a quarta alternativa?");
                        etAlternativa4.requestFocus();
                    }
                    else if(etAlternativa5 == null || etAlternativa5.getText().toString().equals("")) {
                        mostrarErro("Falta apenas a última alternativa!");
                        etAlternativa5.requestFocus();
                    }
                    else if(spResposta.getSelectedItem() == null || spResposta.getSelectedItem().equals("")) {
                        mostrarErro("A Resposta?");
                    }
                    else
                    {

                        Questoes q = viewToQuestao();

                        long res = ds.adicionarQuestao(q);

                        if(res > 0) {
                            Toast.makeText(getActivity(), "Questão Enviada com sucesso!", Toast.LENGTH_LONG).show();

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                            //alertDialog.setTitle("Titulo");
                            alertDialog.setMessage("Sugestão cadastrada com sucesso!");
                            alertDialog.setNeutralButton("OK", new  DialogInterface.OnClickListener() {
                                public  void  onClick(DialogInterface dialog, int  whichButton) {
                                    getActivity().finish();
                                }
                            });

                            alertDialog.show();

                            //Intent intent = new Intent(getActivity(), SugestoesActivity.class);
                            //startActivity(intent);
                        }

                    }

                }
            });

            return rootView;
        }

        public Questoes viewToQuestao() {

            Questoes questao = new Questoes();

            EditText etAno = (EditText) getActivity().findViewById(R.id.etAno);
            Integer ano = Integer.parseInt(etAno.getText().toString());
            questao.setAno(ano);

            Spinner spCategoria = (Spinner) getActivity().findViewById(R.id.spCategoria);

            String strCategoria = (String) spCategoria.getSelectedItem();
            questao.setCategoria(strCategoria);

            EditText etCargo = (EditText) getActivity().findViewById(R.id.etCargo);
            questao.setCargo(etCargo.getText().toString());

            EditText etInstituicao = (EditText) getActivity().findViewById(R.id.etInstituicao);
            questao.setInstituicao(etInstituicao.getText().toString());

            EditText etOrgao = (EditText) getActivity().findViewById(R.id.etOrgao);
            questao.setOrgao(etOrgao.getText().toString());

            EditText etQuestao = (EditText) getActivity().findViewById(R.id.etQuestao);
            questao.setCabecalho(etQuestao.getText().toString());

            List<String> alternativas = new ArrayList<String>();

            EditText etAlternativa1 = (EditText) getActivity().findViewById(R.id.etAlternativa1);
            alternativas.add(etAlternativa1.getText().toString());

            EditText etAlternativa2 = (EditText) getActivity().findViewById(R.id.etAlternativa2);
            alternativas.add(etAlternativa2.getText().toString());

            EditText etAlternativa3 = (EditText) getActivity().findViewById(R.id.etAlternativa3);
            alternativas.add(etAlternativa3.getText().toString());

            EditText etAlternativa4 = (EditText) getActivity().findViewById(R.id.etAlternativa4);
            alternativas.add(etAlternativa4.getText().toString());

            EditText etAlternativa5 = (EditText) getActivity().findViewById(R.id.etAlternativa5);
            alternativas.add(etAlternativa5.getText().toString());

            questao.setAlternativas(alternativas);

            Spinner spResposta = (Spinner) getActivity().findViewById(R.id.spResposta);
            questao.setResposta(spResposta.getSelectedItemPosition());

            return questao;

        }

        public void mostrarErro(String msg){

            Crouton.makeText(getActivity(), msg, Style.ALERT).show();
        }

    }

}
