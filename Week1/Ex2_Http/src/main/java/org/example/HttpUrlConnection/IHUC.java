package org.example.HttpUrlConnection;

/**
 * @Overview: Interface http url connection
 */
public interface IHUC {
    void createRequest();
    void configRequest();
    void handleJsonResponse();
    void handleFailedRequest();
}
