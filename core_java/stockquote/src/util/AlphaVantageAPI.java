package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Quote;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlphaVantageAPI {

    public static Quote fetchQuote(String symbol) {
        String apiKey = "e70b1aa95fmsh454b0acde530880p14b4f3jsn0ea69687025e";
        String url = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body()).path("Global Quote");

                return new Quote(
                        rootNode.path("01. symbol").asText(),
                        rootNode.path("02. open").asDouble(),
                        rootNode.path("03. high").asDouble(),
                        rootNode.path("04. low").asDouble(),
                        rootNode.path("05. price").asDouble(),
                        rootNode.path("06. volume").asInt(),
                        new SimpleDateFormat("yyyy-MM-dd").parse(rootNode.path("07. latest trading day").asText()),
                        rootNode.path("08. previous close").asDouble(),
                        rootNode.path("09. change").asDouble(),
                        rootNode.path("10. change percent").asText(),
                        new Timestamp(System.currentTimeMillis())
                );
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.io.IOException;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// public class AlphaVantageAPI {
//     public static void main(String[] args) {
//         String symbol = "MSFT"; // Change this symbol to the stock you want
//         String apiKey = "e70b1aa95fmsh454b0acde530880p14b4f3jsn0ea69687025e"; // Replace with your Alpha Vantage API key
//         String url = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json";
//         // Build the HttpRequest
//         HttpRequest request = HttpRequest.newBuilder()
//                 .uri(URI.create(url))
//                 .header("X-RapidAPI-Key", apiKey)
//                 .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
//                 .GET()
//                 .build();
//         try {
//             // Send the request and get the response
//             HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//             // Check if the response is successful
//             if (response.statusCode() == 200) {
//                 String responseBody = response.body();
//                 System.out.println("Response Body: " + responseBody);
//                 // Parse the JSON response using Jackson
//                 ObjectMapper objectMapper = new ObjectMapper();
//                 JsonNode rootNode = objectMapper.readTree(responseBody);
//                 // Extract and print specific data from the JSON
//                 JsonNode globalQuoteNode = rootNode.path("Global Quote");
//                 String symbolFromAPI = globalQuoteNode.path("01. symbol").asText();
//                 String openPrice = globalQuoteNode.path("02. open").asText();
//                 String price = globalQuoteNode.path("05. price").asText();
//                 String changePercent = globalQuoteNode.path("10. change percent").asText();
//                 // Print out the extracted values
//                 System.out.println("Symbol: " + symbolFromAPI);
//                 System.out.println("Open Price: " + openPrice);
//                 System.out.println("Current Price: " + price);
//                 System.out.println("Change Percent: " + changePercent);
//             } else {
//                 System.out.println("Error: " + response.statusCode());
//             }
//         } catch (IOException | InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }
// // java -cp ".;lib/*" src/AlphaVantageAPI.java
// // javac -cp ".;lib/*" src/AlphaVantageAPI.java -d build/
