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
import java.util.Date;
import java.util.Optional;

public class QuoteHttpHelper {

    private static final String API_URL = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=";
    private static final String API_KEY = "your_api_key";

    public Optional<Quote> getQuote(String ticker) {
        String url = API_URL + ticker + "&datatype=json";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode globalQuoteNode = rootNode.path("Global Quote");

                Quote quote = new Quote(
                        globalQuoteNode.path("01. symbol").asText(),
                        globalQuoteNode.path("02. open").asDouble(),
                        globalQuoteNode.path("03. high").asDouble(),
                        globalQuoteNode.path("04. low").asDouble(),
                        globalQuoteNode.path("05. price").asDouble(),
                        globalQuoteNode.path("06. volume").asInt(),
                        new Date(),
                        globalQuoteNode.path("08. previous close").asDouble(),
                        globalQuoteNode.path("09. change").asDouble(),
                        globalQuoteNode.path("10. change percent").asText(),
                        new Timestamp(System.currentTimeMillis())
                );

                return Optional.of(quote);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
