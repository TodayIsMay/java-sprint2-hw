package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private URI url;
    private String apiKey;
    private HttpClient client;

    public KVTaskClient(URI url) {
        this.url = url;
        client = HttpClient.newHttpClient();
        URI register = URI.create(url + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(register)
                .GET()
                .header("Accept", "application/json")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(response.body());
            if(element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                apiKey = jsonObject.getAsString();
                System.out.println(apiKey);
            } else {
                apiKey = element.getAsString();
                System.out.println("API_KEY: " + apiKey);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String json) {
        URI put = URI.create(url + "/save/" + key + "?API_KEY=" + apiKey);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(put)
                .POST(body)
                .build();
        try {
            HttpResponse<String> response = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status code put = " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String load(String key) {
        URI get = URI.create(url + "/load/" + key + "?API_KEY=" + apiKey);
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(get)
                .GET()
                .build();
        String response = "";
        try {
            HttpResponse<String> httpResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            response = httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }
}

