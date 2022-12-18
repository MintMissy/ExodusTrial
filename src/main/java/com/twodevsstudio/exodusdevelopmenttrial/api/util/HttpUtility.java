package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class HttpUtility {
  public JSONObject sendJsonGetRequest(String url)
      throws IOException, URISyntaxException, InterruptedException, ParseException {

    HttpClient client =
        HttpClient.newBuilder()
            .connectTimeout(Duration.of(5000, TimeUnit.MILLISECONDS.toChronoUnit()))
            .build();
    HttpRequest request =
        HttpRequest.newBuilder()
            .GET()
            .header("accept", "application/json")
            .uri(new URI(url))
            .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return (JSONObject) JSONValue.parseWithException(response.body());
  }
}
