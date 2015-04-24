package ria.especializacao.inf.br.fragment;

/**
 * Created by luiz on 22/04/15.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.br.database.SugestaoDAO;
import ria.especializacao.inf.br.model.Questoes;
import ria.especializacao.inf.br.quiz.CategoriaActivity;
import ria.especializacao.inf.br.quiz.EnviarQuestaoActivity;
import ria.especializacao.inf.br.quiz.QuestaoActivity;
import ria.especializacao.inf.br.quiz.R;

public class SugestoesFragment extends Fragment {

    private ArrayAdapter<String> sugestoesAdapter;
    private SugestaoDAO ds;
    private List<String> sugestoesList;

    public SugestoesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sugestoesList = getQuestoes();

        View rootView = inflater.inflate(R.layout.fragment_sugestoes, container, false);

        if(sugestoesList.size() > 0) {


            sugestoesAdapter = new ArrayAdapter<String>
                    (
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            sugestoesList
                    );
        }
        else
        {
            TextView tvMsg = (TextView) rootView.findViewById(R.id.msg);
            tvMsg.setVisibility(View.VISIBLE);
        }


        ListView listView = (ListView) rootView.findViewById(R.id.sugestoesListView);
        listView.setAdapter(sugestoesAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                if(sugestoesList.size() > 0){

                    Toast.makeText(getActivity(), sugestoesAdapter.getItem(position), Toast.LENGTH_SHORT).show();

                }


            }
        });

        return rootView;
    }

    private List<String> getQuestoes(){

        ds = new SugestaoDAO(getActivity());
        ds.open();

        List<Questoes> questoes = ds.getAllQuestoes();

        int tam = questoes.size();

        List<String> strQuestoes = new ArrayList<String>();

        for(Questoes q : questoes) {
            strQuestoes.add(q.getAno()+" - "+q.getCabecalho());
        }

        return strQuestoes;

    }

}