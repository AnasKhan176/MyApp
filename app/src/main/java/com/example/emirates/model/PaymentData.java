package com.example.emirates.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentData {

    @SerializedName("paymenttype")
    private List<PaymentModel> items;

    public List<PaymentModel> getItems() {
        return items;
    }

}