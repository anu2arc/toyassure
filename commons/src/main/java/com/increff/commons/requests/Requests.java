package com.increff.commons.requests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.*;

public class Requests {
    public static <OkHttpClient> String post(String url, String body) throws Exception {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        com.squareup.okhttp.OkHttpClient client=new com.squareup.okhttp.OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, body); // new
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
    public static String get(String url) throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static <T> String objectToJsonString(T obj) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(obj);
        System.out.println(json);
        return json;
    }
}
