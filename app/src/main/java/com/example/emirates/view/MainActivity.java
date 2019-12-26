package com.example.emirates.view;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.emirates.R;
import com.example.emirates.databinding.ActivityMainBinding;
import com.example.emirates.viewmodel.ExpenseViewModel;
import com.example.emirates.viewmodel.MyViewModelFactory;

import java.util.Calendar;

public class MainActivity extends BaseActivity {
    private ExpenseViewModel eExpenseViewModel;
    private String p_id = "";
    Calendar date;
    ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (Build.VERSION.SDK_INT < 16) {
            removeStatusBar();
        } else {
            removeStatusBar();
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        showBackButton(true);
        setActivityTitle(getString(R.string.main_title));
        initData();
    }

    private void initData() {
        eExpenseViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication())).get(ExpenseViewModel.class);
        date = Calendar.getInstance();
        toastLong(this, "Total saved Data : " + String.valueOf(eExpenseViewModel.getDataCount()));
    }

    public void actionDate(View view) {
        datepicker(date, this, mMainBinding.etDate, 5);
    }

    public void actionPayment(View view) {
        if (eExpenseViewModel.checkPData(this, eExpenseViewModel.getPaymentType())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this);
            builder.setTitle(getString(R.string.payment_mode));
            builder.setItems(eExpenseViewModel.getPaymentMode(),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            if (which >= 0) {
                                dialog.dismiss();
                                mMainBinding.etPaymentType.setText(eExpenseViewModel.getPaymentMode()[which]);
                                p_id = eExpenseViewModel.getPaymentModeID()[which];
                            }
                        }
                    });
            builder.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (eExpenseViewModel.validate(this, mMainBinding.etDate.getText().toString(),
                        mMainBinding.etPaymentType.getText().toString(),
                        mMainBinding.etFrom.getText().toString(),
                        mMainBinding.etTo.getText().toString(), mMainBinding.etGrossAmt.getText().toString())) {
                    boolean status = eExpenseViewModel.addExpense(mMainBinding.etDate.toString(),
                            mMainBinding.etPaymentType.getText().toString(),
                            p_id, mMainBinding.etFrom.getText().toString(),
                            mMainBinding.etTo.getText().toString(), mMainBinding.etGrossAmt.getText().toString());
                    if (status == true) {
                        toastShort(this, getString(R.string.data_save));
                        mMainBinding.etDate.setText("");
                        mMainBinding.etPaymentType.setText("");
                        mMainBinding.etFrom.setText("");
                        mMainBinding.etTo.setText("");
                        mMainBinding.etGrossAmt.setText("");
                        p_id = "";
                    }
                }
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
