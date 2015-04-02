package ria.especializacao.inf.br.httpconnection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ronie on 28/03/2015.
 */
public class QuizService extends AsyncTask<Void, Void, Void>
{

    @Override
    protected Void doInBackground(Void... params)
    {
        /*if(params == null)
        {
            return null;
        } */

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String quizJsonStr = null;

        try
        {
            final String QUIZ_BASE_URL = "http://www.conquiz.com.br/questaos/json/1";
            URL url = new URL(QUIZ_BASE_URL.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
            {
                // Nothing to do.
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

            quizJsonStr = buffer.toString();

            Log.v("RETORNO", "Quiz string: " + quizJsonStr);

            //ParserJson json = new ParserJson();

            //return json.readJsonStream(inputStream);
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
        return null;
    }
}