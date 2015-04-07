package ria.especializacao.inf.br.quiz;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ria.especializacao.inf.br.database.SessaoDAO;
import ria.especializacao.inf.br.model.Sessao;
import ria.especializacao.inf.br.quiz.R;


public class EstatisticaActivity extends ActionBarActivity {

    private static SessaoDAO sessaoDAO;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ria.especializacao.inf.br.quiz.R.layout.activity_estatistica);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(ria.especializacao.inf.br.quiz.R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(ria.especializacao.inf.br.quiz.R.menu.menu_estatistica, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == ria.especializacao.inf.br.quiz.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private TextView msgAcertos;
        private TextView msgErros;
        private TextView msgTotal;
        private  View rootView;
        RelativeLayout linearChart;


        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_estatistica, container, false);

            msgAcertos = (TextView) rootView.findViewById(R.id.acertos);
            msgErros   = (TextView) rootView.findViewById(R.id.erros);
            msgTotal   = (TextView) rootView.findViewById(R.id.total);

            sessaoDAO = new SessaoDAO(getActivity());

            //Log.v("***Acertos",+sessaoDAO.getSessoes().getQtdAcertos()+" Erros:"+sessaoDAO.getSessoes().getQtdErros());


            msgAcertos.setText(msgAcertos.getText()+String.valueOf(sessaoDAO.getSessoes().getQtdAcertos()));
            msgErros.setText(msgErros.getText()+String.valueOf(sessaoDAO.getSessoes().getQtdErros()));
            msgTotal.setText(msgTotal.getText()+String.valueOf(sessaoDAO.getSessoes().getQtdAcertos()+sessaoDAO.getSessoes().getQtdErros()));

            linearChart = (RelativeLayout) rootView.findViewById(R.id.linearChart);

            int opcoes[] = {sessaoDAO.getSessoes().getQtdAcertos(), sessaoDAO.getSessoes().getQtdErros()};


                drawChartAcertos(sessaoDAO.getSessoes().getQtdAcertos());
                drawChartErros(sessaoDAO.getSessoes().getQtdErros());


            return rootView;
        }

        public void drawChartAcertos( int acertos) {

                View view = new View(getActivity());

                view.setBackgroundColor(Color.GREEN);

                view.setLayoutParams(new RelativeLayout.LayoutParams(100, acertos*10));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                params.setMargins(3, 0, 0, 0); // substitute parameters for left,
                // top, right, bottom
                view.setLayoutParams(params);
                linearChart.addView(view);
        }

        public void drawChartErros( int erros) {

            View view = new View(getActivity());

            view.setBackgroundColor(Color.RED);

            view.setLayoutParams(new RelativeLayout.LayoutParams(100, erros * 10));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                    .getLayoutParams();
            params.setMargins(200, 0, 0, 0); // substitute parameters for left,
            // top, right, bottom
            view.setLayoutParams(params);
            linearChart.addView(view);
        }

    }
}
