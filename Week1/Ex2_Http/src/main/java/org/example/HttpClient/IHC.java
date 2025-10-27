package org.example.HttpClient;

public interface IHC {
    void createClient();

    void createRequest();

    void handleSyncResponse();

    void handleAsyncResponse();
}
