package ria.especializacao.inf.ufg.br.quizz;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment
    {
        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            ListView menuPrincipal;

            final String[] itemName =
            {
                "Começar",
                "Enviar Questão",
                "Favoritos",
                "Estatísticas",
            };

            Integer[] imgId =
            {
                R.mipmap.ic_clock,
                R.mipmap.ic_clock,
                R.mipmap.ic_clock,
                R.mipmap.ic_clock
            };

            /*List<String> menuPrincipal = new ArrayList<String>();
            menuPrincipal.add("Começar");
            menuPrincipal.add("Favoritos");
            menuPrincipal.add("Enviar Questão");
            menuPrincipal.add("Estatísticas"); */

            View rootView = inflater.inflate(R.layout.principal_fragment, container, false);

            CustomAdapter adapter = new CustomAdapter(getActivity(), itemName, imgId);
            menuPrincipal = (ListView) rootView.findViewById(R.id.main_list_view);
            menuPrincipal.setAdapter(adapter);

            menuPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = itemName[+position];
                    Toast.makeText(PrincipalActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                }
            });

            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (
                    this.getActivity(),
                    R.layout.main_item_list,
                    menuPrincipal
            );

            ListView listView = (ListView) rootView.findViewById(R.id.main_list_view);
            listView.setAdapter(adapter);*/

            return rootView;
        }
    }
}
