package ria.especializacao.inf.ufg.br.quizz;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.br.database.QuizBD;
import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.model.Sessao;
import ria.especializacao.inf.br.quiz.R;


public class EstatisticaActivity extends ActionBarActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_estatistica, menu);
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
    public static class PlaceholderFragment extends Fragment {

        private ListView mainListViewEstatistica;
        String[] strListView;


        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_estatistica, container, false);

            SessaoDAO sessaoDAO = new SessaoDAO(getActivity());

            /*List<Sessao> lista = sessaoDAO.getSessoes();

            strListView = new String[lista.size()];

            for (int i = 0; i < lista.size(); i++){
                strListView[i] = lista.get(i).getData()+" "+lista.get(i).getHoraInicial()+" "+lista.get(i).getHoraFinal();
            }*/

            mainListViewEstatistica = (ListView) rootView.findViewById(R.id.mainListViewEstatistica);

            ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, strListView);

            mainListViewEstatistica.setAdapter(objAdapter);

            mainListViewEstatistica.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }
    }
}
