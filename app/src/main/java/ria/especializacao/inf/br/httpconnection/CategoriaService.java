package ria.especializacao.inf.br.httpconnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Ronie on 31/03/2015.
 */
public class CategoriaService extends AsyncTask<Void, Void, String[]>
{
    ListView listView;

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

    @Override
    protected void onPostExecute(String[] result)
    {

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