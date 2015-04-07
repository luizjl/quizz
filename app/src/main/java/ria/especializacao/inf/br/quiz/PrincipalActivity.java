package ria.especializacao.inf.br.quiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.model.Sessao;
import ria.especializacao.inf.br.quiz.R;

public class PrincipalActivity extends ActionBarActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ConQuiz TI");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0,191,255)));

        if (savedInstanceState == null)
        {
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

        ListView list;

        String[] opcoes =
                {
                    "Começar",
                    "Favoritos",
                    "Enviar Questão",
                    "Desempenho"
                };

        Integer[] imageId =
                {
                    R.drawable.ic_school_grey600_48dp,
                    R.drawable.ic_grade_grey600_48dp,
                    R.drawable.ic_forum_grey600_48dp,
                    R.drawable.ic_trending_up_grey600_48dp
                };

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.principal_fragment, container, false);

            /* mainListView = (ListView) rootView.findViewById(R.id.mainListView);
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
                            Intent intent = new Intent(getActivity(), CategoriaActivity.class);
                            startActivity(intent);
                            //Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                        case "Favoritos":
                            Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                        case "Enviar Questão":
                            Intent intentEQ = new Intent(getActivity(), EnviarQuestaoActivity.class);
                            startActivity(intentEQ);
                            break;
                        case "Desempenho":
                            intent = new Intent(getActivity(), EstatisticaActivity.class);
                            startActivity(intent);
                            //Toast.makeText(getActivity(), "Você Clicou Em " + strListView[position], Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }); */

            CustomList adapter = new
                    CustomList(getActivity(), opcoes, imageId);
            list=(ListView)rootView.findViewById(R.id.mainListView);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id)
                {
                    //Toast.makeText(getActivity(), "You Clicked at " +opcoes[position], Toast.LENGTH_SHORT).show();
                    switch (opcoes[position])
                    {
                        case "Começar":
                            Intent intent = new Intent(getActivity(), CategoriaActivity.class);
                            startActivity(intent);
                            break;
                        case "Favoritos":
                            Toast.makeText(getActivity(), "Atividade em Construção", Toast.LENGTH_SHORT).show();
                            break;
                        case "Enviar Questão":
                            Intent intentEQ = new Intent(getActivity(), EnviarQuestaoActivity.class);
                            startActivity(intentEQ);
                            break;
                        case "Desempenho":
                            intent = new Intent(getActivity(), EstatisticaActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });

            return rootView;
        }
    }

    public static class CustomList extends ArrayAdapter<String>
    {
        private final Activity context;
        private final String[] web;
        private final Integer[] imageId;

        public CustomList(Activity context,
                          String[] web, Integer[] imageId)
        {
            super(context, R.layout.main_listview_layout, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.main_listview_layout, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.mainText);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.mainImg);
            txtTitle.setText(web[position]);
            imageView.setImageResource(imageId[position]);
            return rowView;
        }
    }
}
