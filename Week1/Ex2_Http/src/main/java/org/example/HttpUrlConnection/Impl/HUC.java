package org.example.HttpUrlConnection.Impl;

import org.example.HttpUrlConnection.HttpMethod.httpMethod;
import org.example.HttpUrlConnection.IHUC;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class HUC implements IHUC {
    private URL url;
    private HttpURLConnection httpURLConnection;
    private String method;

    public HUC(String URL, String method) throws MalformedURLException {
        this.url = new URL(URL);
        this.method = method;
    }

    @Override
    public void createRequest() {
        try {
            // thiết lập và mở kênh giao tiếp logic với máy chủ,
            // nhưng chưa gửi yêu cầu HTTP thực sự qua mạng.
            // no support for HTTP/2.
            httpURLConnection = (HttpURLConnection) url.openConnection();
            if(this.method.equals(httpMethod.GET.name())) {
                httpURLConnection.setRequestMethod("GET");
            }
            if(this.method.equals(httpMethod.POST.name())) {
                httpURLConnection.setRequestMethod("POST");
            }
            if(this.method.equals(httpMethod.PUT.name())) {
                httpURLConnection.setRequestMethod("PUT");
            }
            if(this.method.equals(httpMethod.PATCH.name())) {
                httpURLConnection.setRequestMethod("PATCH");
            }
        } catch (IOException e) {
            throw new RuntimeException("Can not create connection, may be wrong URL or Method");
        }
    }

    @Override
    public void configRequest() {}

    @Override
    public void handleJsonResponse() {
        // Getting the inputstream of the open connection
        try {
            // httpURLConnection.getInputStream(): HTTP request được gửi đi
            // Get the HTTP Response Stream
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream()));

            // Read the Entire Response into a String
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferReader.readLine()) != null) {
                response.append(line);
            }
            bufferReader.close();
            httpURLConnection.disconnect();

            // Parse the JSON
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response.toString());

            FileWriter writer = new FileWriter("output.txt");
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                long userId = (long) jsonObject.get("userId");
                long id = (long) jsonObject.get("id");
                String title = (String) jsonObject.get("title");

                // In dữ liệu ra màn hình
                System.out.println("UserId: " + userId);
                System.out.println("Id: " + id);
                System.out.println("Title: " + title);
                System.out.println("----------------------");

                // ghi ra file
                writer.write("UserId: " + jsonObject.get("userId") + "\n");
                writer.write("Id: " + jsonObject.get("id") + "\n");
                writer.write("Title: " + jsonObject.get("title") + "\n");
                writer.write("---------------------------\n");

            }

            writer.close();


        } catch (IOException | ParseException e) {
            throw new RuntimeException("No response");
        }
    }

    @Override
    public void handleFailedRequest() {

    }



    // Query parameters → appended to the URL (for GET requests)
    // Request body parameters → written to the body (for POST or PUT requests)
    private void addRequestParameter() {
        if (method.equals("GET")) {}
        if (method.equals("POST")) {}
    }

    // Set http request header
    private void setRequestHeader() {}

    // Config Timeout
    private void configTimeout() {}

    // Handling Cookie
    private void handleCookie() {}

    // Handle Redirects
    private void handleRedirects() {}

}