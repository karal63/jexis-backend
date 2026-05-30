package com.jexis.jexis_backend.stripe.infrastructure;

public class StripeClient {
    private final String apiKey;

    public StripeClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
