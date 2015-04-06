package ria.especializacao.inf.br.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ria.especializacao.inf.br.database.SugestaoDAO;
import ria.especializacao.inf.br.model.Questoes;
import ria.especializacao.inf.br.quiz.R;

/**
 * Created by luiz on 05/04/15.
 */
public class ListaQuestoesDialogFragment extends DialogFragment {

    private SugestaoDAO ds;

    public static ListaQuestoesDialogFragment newInstance() {
        return new ListaQuestoesDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_questao, container, false);

        ds = new SugestaoDAO(getActivity());
        ds.open();

        List<Questoes> questoes = ds.getAllQuestoes();

        RelativeLayout fl = (RelativeLayout) v.findViewById(R.id.meuFrameLayout);

        int tam = questoes.size();

        TextView[] t = new TextView[tam];

        int i = 0;
        int prevTextViewId = 0;

        for(Questoes q : questoes) {
            Log.v("QUESTAO", " " + q.getId() + " - " + q.getCabecalho());

            final TextView rowTextView = new TextView(getActivity());

            int curTextViewId = prevTextViewId + 1;

            rowTextView.setId(curTextViewId);
            final RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.addRule(RelativeLayout.BELOW, prevTextViewId);
            rowTextView.setLayoutParams(params);

            prevTextViewId = curTextViewId;


            // set some properties of rowTextView or something
            rowTextView.setText(q.getId() + " - " + q.getCabecalho());


            // add the textview to the linearlayout
           // myLinearLayout.addView(rowTextView);

            // save a reference to the textview for later
            t[i] = rowTextView;

            fl.addView(t[i], params);
            i++;
        }

        return v;
    }

}
