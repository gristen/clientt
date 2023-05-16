package com.example.clieeeent.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HTTPUtils {
    OkHttpClient client = new OkHttpClient();
    public String get(String url, String args) throws IOException {
        Request req = new Request.Builder().url(url+args).build();
        try (Response response = client.newCall(req).execute()) {
            return response.body().string();
        }
    }
}
