package com.example.demo.service;

import com.example.demo.configuration.PayPalClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    @Autowired
    private PayPalClient payPalClient;

    public String createPayment(Double amount, String currency, String returnUrl, String cancelUrl) throws Exception {
        String accessToken = payPalClient.getAccessToken();

        OkHttpClient client = new OkHttpClient();

        String paymentJson = "{"
                + "\"intent\":\"CAPTURE\","
                + "\"purchase_units\":[{"
                + "\"amount\":{"
                + "\"currency_code\":\"" + currency + "\","
                + "\"value\":\"" + amount + "\""
                + "}"
                + "}],"
                + "\"application_context\":{"
                + "\"return_url\":\"" + returnUrl + "\","
                + "\"cancel_url\":\"" + cancelUrl + "\""
                + "}"
                + "}";

        RequestBody body = RequestBody.create(paymentJson, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(payPalClient.getBaseUrl() + "/v2/checkout/orders")
                .post(body)
                .header("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Failed to create payment: " + response.body().string());
        }

        JsonNode jsonResponse = new ObjectMapper().readTree(response.body().string());

        if (jsonResponse.has("links")) {
            return jsonResponse.get("links").get(1).get("href").asText();
        } else {
            throw new RuntimeException("Error al procesar el pago: " + jsonResponse);
        }
    }

    public boolean verifyPayment(String paymentId) throws Exception {
        String accessToken = payPalClient.getAccessToken();

        OkHttpClient client = new OkHttpClient();

        String url = payPalClient.getBaseUrl() + "/v2/checkout/orders/" + paymentId;

        // Construir la solicitud GET para verificar el estado de la orden
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .build();


        Response response = client.newCall(request).execute();


        if (!response.isSuccessful()) {

            throw new RuntimeException("Error al verificar el pago: " + response.body().string());
        }


        String responseBody = response.body().string();


        System.out.println("Respuesta de PayPal: " + responseBody);


        JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);

        String status = jsonResponse.get("status").asText();
        if ("APPROVED".equals(status)) {
            return capturePayment(paymentId, accessToken);
        } else if ("COMPLETED".equals(status)) {
            return true;
        } else {
            System.out.println("Estado inesperado del pago: " + status);
            return false;
        }
    }

    private boolean capturePayment(String paymentId, String accessToken) throws Exception {
        String url = payPalClient.getBaseUrl() + "/v2/checkout/orders/" + paymentId + "/capture";

        // Construir la solicitud POST para capturar el pago
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("", MediaType.parse("application/json")))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        // Ejecutar la solicitud de captura
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        // Comprobar si la respuesta es exitosa
        if (!response.isSuccessful()) {
            System.out.println("Error al capturar el pago: " + response.body().string());
            return false;
        }

        String responseBody = response.body().string();

        System.out.println("Respuesta de captura: " + responseBody);

        JsonNode jsonResponse = new ObjectMapper().readTree(responseBody);

        if ("COMPLETED".equals(jsonResponse.get("status").asText())) {
            return true;
        } else {
            System.out.println("Estado después de la captura: " + jsonResponse.get("status").asText());
            return false;
        }
    }




}
