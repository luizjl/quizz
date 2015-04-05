package ria.especializacao.inf.br.quiz;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ria.especializacao.inf.br.quiz.R;

public class DesempenhoActivity extends ActionBarActivity {

    static Object[] dados = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desempenho);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_desempenho, menu);
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

        private TableRow tabela;
        private TextView msgText;
        private String[] cabecalho = {"Data","Inicio","Fim","Acertos","Erros","Questoes"};

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_desempenho, container, false);


            tabela = (TableRow) rootView.findViewById(R.id.cabecalho);

            for (int i = 0 ; i < cabecalho.length; i++){
                msgText = new TextView(getActivity());
                msgText.setPadding(1,5,3,2);
                msgText.setGravity(Gravity.RIGHT);
                msgText.setTextSize(16);
                msgText.setId(i);
                msgText.setText(cabecalho[i].toString());
                tabela.addView(msgText);
            }


            tabela = (TableRow) rootView.findViewById(R.id.tabela);

            for (int i = 0 ; i < dados.length; i++){
                msgText = new TextView(getActivity());
                msgText.setGravity(Gravity.RIGHT);
                msgText.setTextSize(12);
                msgText.setId(i);
                msgText.setText(dados[i].toString());
                tabela.addView(msgText);
            }


           /* msgText = (TextView) rootView.findViewById(R.id.data);
            msgText.setTextSize(10);
            msgText.setText(dados[0].toString());

            msgText = (TextView) rootView.findViewById(R.id.inicio);
            msgText.setTextSize(10);
            msgText.setText(dados[1].toString());

            msgText = (TextView) rootView.findViewById(R.id.fim);
            msgText.setTextSize(10);
            msgText.setText( dados[2].toString() );

            msgText = (TextView) rootView.findViewById(R.id.acertos);
            msgText.setTextSize(10);
            msgText.setText(dados[3].toString());

            msgText = (TextView) rootView.findViewById(R.id.erros);
            msgText.setTextSize(10);
            msgText.setText(dados[4].toString());

            msgText = (TextView) rootView.findViewById(R.id.questoes);
            msgText.setTextSize(10);
            msgText.setText(dados[5].toString());
            */
            return rootView;
        }
    }
}
