package com.example.jsonparse;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;

public class parsing {
    /*
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }*/
    public static void main(String[] args) throws IOException, JSONException, ParseException {
        /*
        JSONObject json = readJsonFromUrl("https://api.covid19india.org/data.json");
        System.out.println(json.toString());
        System.out.println(json.get("statewise"));*/
        URL url = new URL("https://api.covid19india.org/data.json");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        String inline="";
        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode" +responsecode);
        else{

            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            System.out.println("\nJSON data in string format");
            System.out.println(inline);
            sc.close();
        }
        JSONParser parse = new JSONParser();
        JSONObject jobj = (JSONObject)parse.parse(inline);
        JSONArray jsonarr_1 = (JSONArray) jobj.get("statewise");
        //Get data for Results array
        System.out.println("state \t confirmed \t recovered \tdeaths" );

        for(int i=0;i<jsonarr_1.size();i++)
        {
//Store the JSON objects in an array
//Get the index of the JSON object and print the values as per the index
            JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
            //System.out.format("%32s%10d%16s", string1, int1, string2);
            System.out.format(jsonobj_1.get("state")+"\t" +jsonobj_1.get("confirmed")+"\t "+jsonobj_1.get("recovered")+"\t " +jsonobj_1.get("deaths")+"\n");
        }

    }

}
