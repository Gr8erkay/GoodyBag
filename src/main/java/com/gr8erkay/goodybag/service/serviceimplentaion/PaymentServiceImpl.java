package com.gr8erkay.goodybag.service.serviceimplentaion;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class PaymentServiceImpl {
    private static final String PAYSTACK_BASE_URL = "https://api.paystack.co";
    private static final String API_KEY = "sk_test_your_secret_key_here";  // Replace with your secret key

    public JSONObject initializePayment(double amount, String email) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = PAYSTACK_BASE_URL + "/transaction/initialize";

        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("amount", (int) (amount * 100));  // Paystack requires amount in kobo

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return new JSONObject(responseBody);
        }
    }

    public JSONObject verifyPayment(String reference) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = PAYSTACK_BASE_URL + "/transaction/verify/" + reference;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return new JSONObject(responseBody);
        }
    }
}
