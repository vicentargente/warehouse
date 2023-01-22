package com.example.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Fetch {
    
    //Realiza un GET a una APIrest a los Wrappers
    public static String get(String route) throws IOException{
        URL url = new URL(String.format("http://localhost:8081%s", route));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            //System.out.println(content.toString());
            return content.toString();
        } else {
            System.out.println("Error al hacer la solicitud: código de estado " + status);
        }
        return null;
    }
}