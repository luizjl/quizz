package ria.especializacao.inf.ufg.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ria.especializacao.inf.ufg.quiz.R;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class CategoriaFragment extends android.support.v4.app.Fragment
{
    private ArrayAdapter<String> categoriaAdapter;

    public CategoriaFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_categoria, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        categoriaAdapter = new ArrayAdapter<String>
                (
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        new ArrayList<String>()
                );

        View rootView = inflater.inflate(R.layout.fragment_categoria, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.categoriaListView);
        listView.setAdapter(categoriaAdapter);

        FetchCategoriaTask fetchCategoriaTask = new FetchCategoriaTask();
        fetchCategoriaTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), "Você Clicou Em " + categoriaAdapter.getItemId(position), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public class FetchCategoriaTask extends AsyncTask<Void, Void, String[]>
    {
        @Override
        public void onPostExecute(String[] result)
        {
            if(result != null)
            {
                categoriaAdapter.clear();

                for(String categoriaStr : result)
                {
                    categoriaAdapter.add(categoriaStr);
                }
            }
        }


        @Override
        protected String[] doInBackground(Void... params)
        {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String categoriaStrJson = null;

            try
            {
                final String QUIZ_BASE_URL = "http://www.conquiz.com.br/categorias/listar";
                URL url = new URL(QUIZ_BASE_URL.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null)
                {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0)
                {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                categoriaStrJson = buffer.toString();

                Log.v("RETORNO", "Categoria string: " + categoriaStrJson);
            }
            catch (IOException ex)
            {
                Log.e("Error", "Erro: "+ex);
            }
            finally
            {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (final IOException e)
                    {
                        Log.e("ERROR", "Error closing stream, ", e);
                    }
                }
            }

            try
            {
                return getCategoriaDataFromJson(categoriaStrJson);
            }
            catch (JSONException e)
            {
                Log.e("ERROR", e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        private String[] getCategoriaDataFromJson(String categoriaStrJson)
                throws JSONException
        {
            JSONObject categoriaJson = new JSONObject(categoriaStrJson);
            JSONArray categoriaArray = categoriaJson.getJSONArray("categorias");

            String[] resultStr = new String[categoriaArray.length()];

            for(int i = 0; i < categoriaArray.length(); i++)
            {
                JSONObject categoriaAux = categoriaArray.getJSONObject(i);
                Log.v("CATEGORIA", categoriaAux.getString("nome"));
                resultStr[i] = categoriaAux.getString("nome");
            }

            return resultStr;
        }
    }
}
