package ria.especializacao.inf.ufg.br.quizz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import ria.especializacao.inf.ufg.httpconnection.QuizService;


public class PrincipalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        QuizService service = new QuizService();
        service.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
    public static class PlaceholderFragment extends Fragment
    {
        private ListView mainListView;
        private String[] strListView;

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.principal_fragment, container, false);

            mainListView = (ListView) rootView.findViewById(R.id.mainListView);
            strListView = getResources().getStringArray(R.array.my_data_list);

            ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this.getActivity(),
                    android.R.layout.simple_list_item_1, strListView);

            mainListView.setAdapter(objAdapter);

            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    switch (strListView[position])
                    {
                        case "Começar":
                            Intent intent = new Intent(getActivity(), CategoriasActivity.class);
                            startActivity(intent);
                            //Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                        case "Favoritos":
                            Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                        case "Enviar Questão":
                            Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                        case "Estatísticas":
                            intent = new Intent(getActivity(), EstatisticaActivity.class);
                            startActivity(intent);
                            //Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            return rootView;
        }
    }
}
