package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Faz a requisição para a API questao 8
            String apiUrl = "http://universities.hipolabs.com/search?country=Brazil";
            String jsonResponse = sendGetRequest(apiUrl);

            // Parseia a resposta JSON
            List<Universidade> universidades = parseJsonResponse(jsonResponse);

            // Imprime a lista de universidades
            for (Universidade universidade : universidades) {
                System.out.println("Nome: " + universidade.getNome());
                System.out.println("URL: " + universidade.getUrl());
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    private static List<Universidade> parseJsonResponse(String jsonResponse) {
        List<Universidade> universidades = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String nome = jsonObject.getString("name");

            // O campo "web_pages" é um array o primeiro elemento será pego se existir
            JSONArray webPagesArray = jsonObject.getJSONArray("web_pages");
            String url = webPagesArray.length() > 0 ? webPagesArray.getString(0) : "";

            Universidade universidade = new Universidade(nome, url);
            universidades.add(universidade);
        }

        return universidades;
    }

}

class Universidade {
    private String nome;
    private String url;

    public Universidade(String nome, String url) {
        this.nome = nome;
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}