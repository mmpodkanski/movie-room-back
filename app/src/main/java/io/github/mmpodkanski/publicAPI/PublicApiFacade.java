package io.github.mmpodkanski.publicAPI;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ThreadLocalRandom;

@Service
class PublicApiFacade {
    HttpClient client = HttpClient.newHttpClient();

    News showNews() throws IOException, InterruptedException {
        String apiKey = "gt2Ezv8EHyPljtLSesAyophGDoDMGZIXpIIEsixkvxc9vi72";
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.currentsapi.services/v1/latest-news?language=en&category=movie&apiKey=" + apiKey))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var jsonObject = new JSONObject(response.body());

        if (!jsonObject.getString("status").equals("ok")) {
            throw new ApiBadRequestException("No news today!");
        }

        JSONArray news = jsonObject.getJSONArray("news");
        int randomNum = ThreadLocalRandom.current().nextInt(0, news.length());

        var jsonRes = news
                .getJSONObject(randomNum);

        return new News(
                jsonRes.getString("author"),
                jsonRes.getString("title"),
                jsonRes.getString("description"),
                jsonRes.getString("published"),
                jsonRes.getString("image"),
                jsonRes.getString("url")
        );
    }

    String showScheduleOfMovies() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.tvmaze.com/schedule"))
                .build();

        return "";
    }

    String searchActorByName(String name) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.tvmaze.com/search/people?q=" + name))
                .build();

        return "";
    }
}
