package com.example.clieeeent.utils;

import okhttp3.*;

import java.io.IOException;

public class HTTPUtils {
    OkHttpClient client = new OkHttpClient();
    public String get(String url, String args) throws IOException {
        Request req = new Request.Builder().url(url+args).build();
        try (Response response = client.newCall(req).execute()) {
            return response.body().string();
        }
    }
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
