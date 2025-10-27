package org.example;

import org.example.HttpUrlConnection.Impl.HUC;

import java.net.MalformedURLException;

// Http URI Connection
public class MainHUC {
    public static void main(String[] args) throws MalformedURLException {
        HUC huc = new HUC("https://jsonplaceholder.typicode.com/albums", "GET");
        huc.createRequest();
        huc.handleJsonResponse();
    }
}