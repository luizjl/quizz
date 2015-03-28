package ria.especializacao.inf.ufg.httpconnection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ria.especializacao.inf.ufg.quizz.model.Questoes;
import ria.especializacao.inf.ufg.utils.ParserJson;

/**
 * Created by Ronie on 28/03/2015.
 */
public class QuizService extends AsyncTask<String, Void, List<Questoes>>
{

    @Override
    protected List<Questoes> doInBackground(String... params)
    {
        if(params == null)
        {
            return null;
        }

        HttpURLConnection urlConnection = null;


        try
        {
            final String QUIZ_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94087&mode=json&units=metric&cnt=7";
            URL url = new URL(QUIZ_BASE_URL.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            ParserJson json = new ParserJson();

            return json.readJsonStream(inputStream);
        }
        catch (IOException ex)
        {
            Log.e("ERRO", "Error: " + ex);
            return null;
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    public void onPostExecute(List<Questoes> result)
    {
        // Criar a logica das perguntas nesse método
    }
}