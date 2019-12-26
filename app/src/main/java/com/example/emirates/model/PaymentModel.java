package com.example.emirates.model;

import com.google.gson.annotations.SerializedName;

public class PaymentModel {
    @SerializedName("pmode")
    private String pMode;

    @SerializedName("pid")
    private String pID;

    public String getpMode() {
        return pMode;
    }

    public void setpMode(String pMode) {
        this.pMode = pMode;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }
}
