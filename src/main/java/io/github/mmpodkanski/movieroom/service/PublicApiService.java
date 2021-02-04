package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiRequestException;
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
public class PublicApiService {
    HttpClient client = HttpClient.newHttpClient();

    public String showNews() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.currentsapi.services/v1/latest-news?" + "apiKey=gt2Ezv8EHyPljtLSesAyophGDoDMGZIXpIIEsixkvxc9vi72"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var jsonObject = new JSONObject(response.body());

        if (!jsonObject.getString("status").equals("ok")) {
            throw new ApiRequestException("No news today!");
        }

        JSONArray news = jsonObject.getJSONArray("news");
        int randomNum = ThreadLocalRandom.current().nextInt(0, news.length());

        var title = news
                .getJSONObject(randomNum)
                .getString("title");

        return title;
    }

    public String showScheduleOfMovies() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.tvmaze.com/schedule"))
                .build();

        return "";
    }

    public String searchActorByName(String name) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.tvmaze.com/search/people?q=" + name))
                .build();

        return "";
    }
}
