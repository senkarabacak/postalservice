package com.disys.postalgui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GuiController {

    @FXML
    private TextField letterName;

    @FXML
    private TextField letterCountry;

    @FXML
    private Button letterSendButton;

    @FXML
    private TextField packageName;

    @FXML
    private TextField packageWeight;

    @FXML
    private Button packageSendButton;

    @FXML
    private ListView<String> statusListView;

    @FXML
    private Button refreshButton;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    protected void onSendLetterButtonClick() {

        String name = letterName.getText();
        String country = letterCountry.getText();


        ObjectNode jsonData = objectMapper.createObjectNode();
        jsonData.put("name", name);
        jsonData.put("country", country);

        try {

            HttpClient client = HttpClient.newHttpClient();


            URI uri = new URI("http://localhost:8081/api/letters/letter");


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData.toString()))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            String responseBody = response.body();
            System.out.println("Response from server: " + responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @FXML
    protected void onSendPackageButtonClick() {

        String name = packageName.getText();
        String weight = packageWeight.getText();


        ObjectNode jsonData = objectMapper.createObjectNode();
        jsonData.put("name", name);
        jsonData.put("weight", weight);

        try {
            // Create an HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Define the URI for your server endpoint
            URI uri = new URI("http://localhost:8081/api/packages/package");

            // Create an HTTP request with JSON data
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData.toString()))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Process the response if needed
            String responseBody = response.body();
            System.out.println("Response from server: " + responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    @FXML
    protected void onRefreshButtonClick() throws URISyntaxException {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/api/status"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("response");

            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Clear the statusListView
            statusListView.getItems().clear();

            // Iterate through the JSON objects and add formatted strings to the list view
            for (JsonNode item : jsonNode) {
                String service = item.get("service").asText();
                int id = item.get("id").asInt();
                String status = item.get("status").asText();
                String name = item.get("name").asText();

                // Format the string and add it to the list view
                String formattedText = service + " [" + id + "] - " + name + " - " + status;
                statusListView.getItems().add(formattedText);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}

