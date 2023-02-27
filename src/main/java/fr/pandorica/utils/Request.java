package fr.pandorica.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Request {
    UUID uuid;
    HttpURLConnection con;
    String context;
    public static String base_url;

    public Request(String context, UUID uuid) throws IOException {
        this.context = context;
        this.uuid= uuid;
        URL url = new URL(base_url + context);
        this.con = (HttpURLConnection) url.openConnection();
    }

    public void addHeader(String header, String value){
        con.setRequestProperty(header, value);
    }

    public JsonObject get() {
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty("PlayerUUID", uuid.toString());
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(100); //VALEUR PAR DEFAULT 5000
            con.setReadTimeout(100);
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(content.toString());
            //String strList = content.toString();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JsonObject getWithHeader(String header, String value) {
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty(header, value);
            con.setRequestProperty("PlayerUUID", uuid.toString());
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(100); //VALEUR PAR DEFAULT 5000
            con.setReadTimeout(100);
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(content.toString());
            //String strList = content.toString();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(JsonObject jsonObject) throws IOException {
        con.setRequestMethod("POST");
        con.setRequestProperty("PlayerUUID", uuid.toString());
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setConnectTimeout(100); //VALEUR PAR DEFAULT 5000
        con.setReadTimeout(100);
        con.setDoInput(true);
        con.setDoOutput(true);
        String jsonInputString = jsonObject.toString();
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        String strList = content.toString();
        return strList;
    }

    public String postWithHeader(JsonObject jsonObject, String header, String value) throws IOException {
        con.setRequestMethod("POST");
        con.setRequestProperty("PlayerUUID", uuid.toString());
        con.setRequestProperty(header, value);
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setConnectTimeout(100); //VALEUR PAR DEFAULT 5000
        con.setReadTimeout(100);
        con.setDoInput(true);
        con.setDoOutput(true);
        String jsonInputString = jsonObject.toString();
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        String strList = content.toString();
        return strList;
    }

    public String getWithHeadersWithJson(JsonObject jsonObject, HashMap<String, String> map) throws IOException {
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("PlayerUUID", uuid.toString());
        con.setDoOutput(true);
        for(Map.Entry<String,String> entry : map.entrySet()){
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        con.setConnectTimeout(100); //VALEUR PAR DEFAULT 5000
        con.setReadTimeout(100);
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        String strList = content.toString();
        return strList;
    }

    public String getWithHeaders(HashMap<String, String> map) throws IOException {
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("PlayerUUID", uuid.toString());
        con.setDoOutput(true);
        for(Map.Entry<String,String> entry : map.entrySet()){
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }
        con.setConnectTimeout(100); //VALEUR PAR DEFAULT 5000
        con.setReadTimeout(100);
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        String strList = content.toString();
        return strList;
    }



}
