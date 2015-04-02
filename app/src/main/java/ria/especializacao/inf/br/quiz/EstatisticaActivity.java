package ria.especializacao.inf.br.quiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.model.Sessao;
import ria.especializacao.inf.ufg.quiz.R;


public class EstatisticaActivity extends ActionBarActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ria.especializacao.inf.ufg.quiz.R.layout.activity_estatistica);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(ria.especializacao.inf.ufg.quiz.R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(ria.especializacao.inf.ufg.quiz.R.menu.menu_estatistica, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == ria.especializacao.inf.ufg.quiz.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ListView mainListViewEstatistica;
        private TextView msgText;
        String[] strListView;


        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_estatistica, container, false);

            msgText = (TextView) rootView.findViewById(R.id.msgText);
            msgText.setVisibility(View.INVISIBLE);

            SessaoDAO sessaoDAO = new SessaoDAO(getActivity());

            final List<Sessao> lista = sessaoDAO.getSessoes();

            Log.v("Lista:", String.valueOf(lista.size()));
            if(lista.size() > 0){

                msgText.setVisibility(View.INVISIBLE);

                strListView = new String[lista.size()];

                for (int i = 0; i < lista.size(); i++){
                    strListView[i] = lista.get(i).getData()+" "+lista.get(i).getHoraInicial()+" "+lista.get(i).getHoraFinal();
                }

                mainListViewEstatistica = (ListView) rootView.findViewById(R.id.mainListViewEstatistica);

                ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, strListView);

                mainListViewEstatistica.setAdapter(objAdapter);

                mainListViewEstatistica.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Intent intent = new Intent(getActivity(), DesempenhoActivity.class);
                        Log.v("Estatistica","Passei aqui na Estatistica");
                        DesempenhoActivity.dados = new Object[9];
                        DesempenhoActivity.dados[0] = lista.get(position).getData();
                        DesempenhoActivity.dados[1] = lista.get(position).getHoraInicial();
                        DesempenhoActivity.dados[2] = lista.get(position).getHoraFinal();
                        DesempenhoActivity.dados[3] = lista.get(position).getQtdAcertos();
                        DesempenhoActivity.dados[4] = lista.get(position).getQtdQuestoes();
                        DesempenhoActivity.dados[5] = lista.get(position).getAcertosEngSoftware();
                        DesempenhoActivity.dados[6] = lista.get(position).getAcertosDesenvolvimento();
                        DesempenhoActivity.dados[7] = lista.get(position).getAcertosRedes();
                        DesempenhoActivity.dados[8] = lista.get(position).getAcertosGovernanca();
                        startActivity(intent);
                        //Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                msgText.setVisibility(View.VISIBLE);
            }

            return rootView;
        }
    }
}
