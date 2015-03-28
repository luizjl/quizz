package ria.especializacao.inf.ufg.utils;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ria.especializacao.inf.ufg.quizz.model.Questoes;

/**
 * Created by danillo on 28/03/2015.
 */
public class PaserJson {

    //Lendo Arquivo JSon
    public List<Questoes> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    //Mapeia objeto JSon para Lista
    public List<Questoes> readMessagesArray(JsonReader reader) throws IOException {
        List<Questoes> messages = new ArrayList<Questoes>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    //Mapeia atribuidos do arquivo Json
    public Questoes readMessage(JsonReader reader) throws IOException {
        int id = 0;
        int ano = 0;
        String cargo = null;
        String instituicao = null;
        String orgao = null;
        String cabecalho = null;
        List<String> alternativas = null;
        int resposta = 0;
        String categoria = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            }else if(name.equals("categoria")){
                categoria = reader.nextString();
            } else if (name.equals("ano")) {
                ano = reader.nextInt();
            } else if( name.equals("cargo")){
                cargo =  reader.nextString();
            }else if(name.equals("instituicao")){
                instituicao =  reader.nextString();
            }else if(name.equals("cabecalho")){
                cabecalho = reader.nextString();
            }else if (name.equals("alternativas") && reader.peek() != JsonToken.NULL) {
                alternativas = readStringArray(reader);
            } else if (name.equals("resposta")) {
                resposta = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Questoes(id , ano, categoria ,cargo,instituicao ,orgao, cabecalho, alternativas, resposta);
    }

    //Mapeia variável aternativas
    public List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> str = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            str.add(reader.nextString());
        }
        reader.endArray();
        return str;
    }

}
