package org.example;

import org.example.HttpClient.Impl.SimpleHC;

// Http Client
public class MainHC {
    public static void main(String[] args) {
        SimpleHC simpleHC = new SimpleHC("https://jsonplaceholder.typicode.com/albums");
        simpleHC.createRequest();
        //simpleHC.handleSyncResponse();
        simpleHC.handleAsyncResponse();
    }
}
