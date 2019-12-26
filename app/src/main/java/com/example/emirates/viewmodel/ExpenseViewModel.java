package com.example.emirates.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.widget.Toast;

import com.example.emirates.R;
import com.example.emirates.database.ExpenseDBHelper;
import com.example.emirates.model.PaymentData;
import com.example.emirates.model.PaymentModel;
import com.example.emirates.utils.JsonDataUtils;
import com.example.emirates.utils.StringUtils;
import com.example.emirates.view.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private ArrayList<PaymentModel> pData;

    ExpenseViewModel(Application application) {
        super(application);
    }

    public List<PaymentModel> getPaymentType() {
        PaymentData model = new Gson().fromJson(JsonDataUtils.getAssetJsonData(getApplication()), PaymentData.class);
        if (pData == null) {
            pData = new ArrayList<>(model.getItems().size());
        }
        pData.clear();
        for (int i = 0; i < model.getItems().size(); i++) {
            pData.add(model.getItems().get(i));
        }
        return model.getItems();
    }


    public String[] getPaymentMode() {
        String[] mode_arr;
        ArrayList<String> pMode = new ArrayList<>(pData.size());
        for (int i = 0; i < pData.size(); i++) {
            pMode.add(pData.get(i).getpMode());
        }
        mode_arr = (String[]) pMode.toArray(new String[pMode.size()]);
        return mode_arr;
    }

    public String[] getPaymentModeID() {
        String[] mode_ID_arr;
        ArrayList<String> pID = new ArrayList<>(pData.size());
        for (int i = 0; i < pData.size(); i++) {
            pID.add(pData.get(i).getpID());
        }
        mode_ID_arr = (String[]) pID.toArray(new String[pID.size()]);
        return mode_ID_arr;
    }

    public boolean addExpense(String date, String pType, String pValue, String from, String to, String grossAmt) {
        return ExpenseDBHelper.getInstance(getApplication()).expenseEntry(date, pType, pValue, from, to, grossAmt);
    }

    public boolean validate(MainActivity ctx, String date, String payment, String from, String to, String amt) {
        if (StringUtils.isNullOrBlank(date)) {
            Toast.makeText(ctx, ctx.getString(R.string.main_date_err), Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isNullOrBlank(payment)) {
            Toast.makeText(ctx, ctx.getString(R.string.main_payment_err), Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isNullOrBlank(from)) {
            Toast.makeText(ctx, ctx.getString(R.string.main_from_err), Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isNullOrBlank(to)) {
            Toast.makeText(ctx, ctx.getString(R.string.main_to_err), Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isNullOrBlank(amt)) {
            Toast.makeText(ctx, ctx.getString(R.string.main_gross_err), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean checkPData(MainActivity ctx, List<PaymentModel> paymentType) {

        if (StringUtils.isNullOrEmptyList(paymentType)) {
            Toast.makeText(ctx, ctx.getString(R.string.main_date_err), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public int getDataCount() {
        return ExpenseDBHelper.getInstance(getApplication()).getDataCount();
    }
}
