package org.example.HttpClient.Impl;

import org.example.HttpClient.IHC;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHC implements IHC {
    private String url;
    private HttpRequest httpRequest;
    private HttpClient client;

    public SimpleHC(String url) {
        this.url= url;
        createClient();
    }

    // The HttpClient is the engine that performs HTTP operations.
    @Override
    public void createClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .proxy(ProxySelector.getDefault())                  // Set proxy
                .followRedirects(HttpClient.Redirect.ALWAYS)        // Set Redirect Policy
                .version(HttpClient.Version.HTTP_2)                 // Set versionn
//                .authenticator(new Authenticator() {              // Authentication
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(
//                                "username",
//                                "password".toCharArray());
//                    }
//                })
                .build();
    }

    // HttpRequest — The Message You Send
    @Override
    public void createRequest() {
        try {
            httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .version(HttpClient.Version.HTTP_2)                         // Set version to request
                    //.header("Authorization", "Bearer " + accessToken)         // Set header
                    .timeout(Duration.of(100, ChronoUnit.SECONDS))       // Set time out
                    .GET()
                    //.POST(HttpRequest.BodyPublishers.noBody())                // Set request body
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // HttpResponse — The Message You Receive
    @Override
    public void handleSyncResponse(){
        try {
            /* The BodyHandler tells the client how to process that body when it arrives.
            BodyHandlers.ofString(): Reads the entire response body as a String
                --> JSON, text, XML, HTML.
            BodyHandlers.ofByteArray(): Reads the entire response body as a byte[].
                --> images, PDFs, or binary downloads.
            BodyHandlers.ofFile(): Automatically saves the response body to a file.
                --> Write in disk
            BodyHandlers.ofInputStream()
            BodyHandlers.discarding()
             */
            // send(…) – synchronously (blocks until the response comes)
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Headers: " + response.headers());
            System.out.println("Body: " + response.body());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Use when you send multiple request, non-blocking
    public void handleAsyncResponse() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<HttpResponse<String>> response1 = HttpClient.newBuilder()
                .executor(executorService)
                .build()
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        // ... Do something else

        response1.thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join(); // Wait for completion

        System.out.println("All is Done");
    }


}
