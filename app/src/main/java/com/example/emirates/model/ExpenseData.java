package com.example.emirates.model;

public class ExpenseData {

    public String eDate;
    public String ePType;
    public String ePValue;
    public String eFrom;
    public String eTo;
    public String eGrossAmt;

    public ExpenseData(String date, String pType, String pValue, String from, String to, String grossAmt) {
        eDate = date;
        ePType = pType;
        ePValue = pValue;
        eFrom = from;
        eTo = to;
        eGrossAmt = grossAmt;
    }
    public ExpenseData(ExpenseData expData) {
        eDate = expData.eDate;
        ePType = expData.ePType;
        ePValue = expData.ePValue;
        eFrom = expData.eFrom;
        eTo = expData.eTo;
        eGrossAmt = expData.eGrossAmt;
    }

}