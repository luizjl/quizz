package ria.especializacao.inf.br.quiz;

import android.app.DialogFragment;
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

import ria.especializacao.inf.br.database.SugestaoDAO;
import ria.especializacao.inf.br.fragment.ListaQuestoesDialogFragment;
import ria.especializacao.inf.br.model.Questoes;

public class EnviarQuestaoActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_questao);

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
        if (id == R.id.action_settings) {
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
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            Spinner spCategoria = (Spinner) rootView.findViewById(R.id.spCategoria);
            ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.categorias_array, android.R.layout.simple_spinner_item);
            adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapterCategoria);
            //spinner.setOnItemSelectedListener(new AlternativatemSelectedListener());
            Button btSalvar = (Button) rootView.findViewById(R.id.btSalvar);
            btSalvar.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                    ds.open();

                    Questoes q = viewToQuestao();

                    long res = ds.adicionarQuestao(q);

                    Log.i("ADICIONANDO", String.valueOf(res));

                    if(res > 0)
                        Toast.makeText(getActivity(), "Questão Enviada com sucesso!", Toast.LENGTH_LONG).show();

                    DialogFragment newFragment = ListaQuestoesDialogFragment.newInstance();
                    newFragment.show(getActivity().getFragmentManager(), "dialog");

                }
            });

            return rootView;
        }

        public Questoes viewToQuestao() {

            Questoes questao = new Questoes();

            EditText etAno = (EditText) getActivity().findViewById(R.id.etAno);
            int ano = Integer.parseInt(etAno.getText().toString());
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

    }

}
