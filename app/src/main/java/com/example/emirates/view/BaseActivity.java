package com.example.emirates.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emirates.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BaseActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener dateOnClick;

    public void removeStatusBar() {
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setActivityTitle(String title) {
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
            actionBar.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showBackButton(boolean isBackButton) {
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(isBackButton);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void toastShort(Activity ctx, String message) {
        Toast toast = null;
        if (!ctx.isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }
            if (message != null && message.length() > 0) {
                toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void toastLong(Activity ctx, String message) {
        Toast toast = null;
        if (!ctx.isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }
            if (message != null && message.length() > 0) {
                toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
         My Calendar Logic
    */
    public void datepicker(Calendar dateCache, final Context c, final EditText tv, final Integer status) {
        Calendar temp = null;
        temp = Calendar.getInstance();
        Calendar dateMin, dateMax;
        switch (status) {

            case 0: //non motor From_Date

                dateMin = getDateMaxMin(temp, 0, 0, 0, 0);
                dateMax = getDateMaxMin(temp, 1, -1, 6, 0);
                commonDatePicker(dateCache, dateMax, dateMin, c, tv);
                break;
            case 1://Motor From_Date
                dateMin = getDateMaxMin(temp, 0, 0, 0, 0);
                dateMax = getDateMaxMin(temp, 1, -1, 6, 0);
                commonDatePicker(dateCache, dateMax, dateMin, c, tv);
                break;
            case 2: // DOB 20years
                dateMax = getDateMaxMin(temp, 0, 0, 0, 20);
                MinMaxDatePicker(dateCache, dateMax, c, tv, 1);
                break;
            case 3: //DOB 18 years
                dateMax = getDateMaxMin(temp, 0, 0, 0, 18);
                MinMaxDatePicker(dateCache, dateMax, c, tv, 1);
                break;
            case 4:
                dateMax = getDateMaxMin(temp, 0, 0, 0, 0);
                MinMaxDatePicker(dateCache, dateMax, c, tv, 1);
                break;
            case 5:
                commonDatePicker(temp, c, tv);
                break;
            case 6:
                dateMin = getDateMaxMin(temp, 0, 0, 0, 0);
                dateMax = getDateMaxMin(temp, 1, -1, 12, 0);
                commonDatePicker(dateCache, dateMax, dateMin, c, tv);

            case 7:
                dateMax = getDateMaxMin(temp, 0, 0, 0, 0);
                dateMin = getDateMaxMin(temp, 0, 0, 0, 1);
                commonDatePicker(dateCache, dateMax, dateMin, c, tv);
                break;
            case 8://Today until a month
                dateMin = getDateMaxMin(temp, 0, 0, 0, 0);
                dateMax = getDateMaxMin(temp, 1, 0, 1, 0);
                commonDatePicker(dateCache, dateMax, dateMin, c, tv);
            case 9:
                dateMin = getDateMaxMin(temp, 0, 0, 0, 0);
                MinMaxDatePicker(dateCache, dateMin, c, tv, 0);
                break;
        }

    }


    public void commonDatePicker(final Calendar dateCache, final Calendar dateMax, final Calendar dateMin, final Context c, final EditText tv) {
        final String[] date = {null};
        final boolean[] check = {false};
        if (dateMin.compareTo(dateCache) > 0) {
            datePickerDialog = new DatePickerDialog(c, R.style.DatePickerDialogTheme, dateOnClick, dateMin.get(Calendar.YEAR), dateMin.get(Calendar.MONTH), dateMin.get(Calendar.DAY_OF_MONTH));

        } else {
            datePickerDialog = new DatePickerDialog(c, R.style.DatePickerDialogTheme, dateOnClick, dateCache.get(Calendar.YEAR), dateCache.get(Calendar.MONTH), dateCache.get(Calendar.DAY_OF_MONTH));
        }
        final Calendar temp = Calendar.getInstance();
        temp.set(dateCache.get(Calendar.YEAR), dateCache.get(Calendar.MONTH), dateCache.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT > 19) {
            datePickerDialog.getDatePicker().setMaxDate(dateMax.getTimeInMillis());
            datePickerDialog.getDatePicker().setMinDate(dateMin.getTimeInMillis());
        }
        datePickerDialog.show();


        dateOnClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {

                Calendar datecheck = Calendar.getInstance();
                datecheck.set(dateMax.get(Calendar.YEAR), dateMax.get(Calendar.MONTH), dateMax.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().clearFocus();
                temp.set(year, monthOfYear, dayOfMonth);

                if (Build.VERSION.SDK_INT > 19) {
                    if (!(temp.compareTo(datecheck) < 0)) {
                        temp.set(dateMax.get(Calendar.YEAR), dateMax.get(Calendar.MONTH), dateMax.get(Calendar.DAY_OF_MONTH));
                    }
                }

            }
        };

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.cancel();
                    tv.setText("");
                }
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {

                    DatePicker datePicker = datePickerDialog
                            .getDatePicker();
                    dateOnClick.onDateSet(datePicker,
                            datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth());


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    date[0] = simpleDateFormat.format(temp.getTime());

                    tv.setText(date[0]);

                    if (dateCompare(temp, dateMax) == 1) {
                        tv.setText("");
                        date[0] = simpleDateFormat.format(dateMax.getTime());

                    } else if (dateCompare(temp, dateMin) == -1) {

                        tv.setText("");
                        date[0] = simpleDateFormat.format(dateMin.getTime());

                    } else {
                        check[0] = true;
                    }

                    if (check[0]) {
                        dateCache.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
                    }


                }
            }
        });

    }

    public void commonDatePicker(final Calendar dateCache, final Context c, final EditText tv) {
        final String[] date = {null};


//            toastTextShortReference(t, c, "dateCacheCheck  " +String.valueOf(dateCache.get(Calendar.MONTH)));
        datePickerDialog = new DatePickerDialog(c, R.style.DatePickerDialogTheme, dateOnClick, dateCache.get(Calendar.YEAR), dateCache.get(Calendar.MONTH), dateCache.get(Calendar.DAY_OF_MONTH));

        final Calendar temp = Calendar.getInstance();
        temp.set(dateCache.get(Calendar.YEAR), dateCache.get(Calendar.MONTH), dateCache.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        datePickerDialog.setCancelable(false);

        datePickerDialog.show();


        dateOnClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {

//                Calendar datecheck = Calendar.getInstance();

                datePickerDialog.getDatePicker().clearFocus();
                temp.set(year, monthOfYear, dayOfMonth);


            }
        };

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.cancel();
                    tv.setText("");
                }
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {

                    DatePicker datePicker = datePickerDialog
                            .getDatePicker();
                    dateOnClick.onDateSet(datePicker,
                            datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth());


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    date[0] = simpleDateFormat.format(temp.getTime());

                    tv.setText(date[0]);
                    dateCache.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));


                }
            }
        });

    }


    public int dateCompare(Calendar a, Calendar b) {

        Long val = null, valA, valB, check = Long.valueOf(86400000);
        int x = 1;
        valA = a.getTimeInMillis();
        valB = b.getTimeInMillis();
        if (valA.compareTo(valB) >= 0) {
            x = 1;
            val = valA - valB;
        } else if (valB.compareTo(valA) >= 0) {
            x = -1;
            val = valB - valA;
        }

        Integer compare = val.compareTo(check);
//        if(compare>Integer.valueOf("0"))
//        {
//            return x;
//        }
//         else
//        {
//            return 0;
//        }
        switch (compare) {

            case 1:
                return x;
            case 0:
            case -1:
                return 0;

        }
        return 5;
    }

    public void MinMaxDatePicker(final Calendar dateCache, final Calendar dateLimit, final Context c, final EditText tv, final Integer status) {

        if ((dateLimit.compareTo(dateCache) > 0 && status == 0) || (dateLimit.compareTo(dateCache) < 0 && status == 1)) {
            datePickerDialog = new DatePickerDialog(c, R.style.DatePickerDialogTheme, dateOnClick, dateLimit.get(Calendar.YEAR), dateLimit.get(Calendar.MONTH), dateLimit.get(Calendar.DAY_OF_MONTH));

        } else {
            datePickerDialog = new DatePickerDialog(c, R.style.DatePickerDialogTheme, dateOnClick, dateCache.get(Calendar.YEAR), dateCache.get(Calendar.MONTH), dateCache.get(Calendar.DAY_OF_MONTH));
        }
        final Calendar temp = Calendar.getInstance();
        temp.set(dateCache.get(Calendar.YEAR), dateCache.get(Calendar.MONTH), dateCache.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        if (status == 1) {
            datePickerDialog.getDatePicker().setMaxDate(dateLimit.getTimeInMillis());
        } else {
            datePickerDialog.getDatePicker().setMinDate(dateLimit.getTimeInMillis());

        }
        datePickerDialog.show();


        dateOnClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {

                datePickerDialog.getDatePicker().clearFocus();
                temp.set(year, monthOfYear, dayOfMonth);

            }
        };
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.cancel();
                }
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {

                    DatePicker datePicker = datePickerDialog
                            .getDatePicker();
                    dateOnClick.onDateSet(datePicker,
                            datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth());
                    String date = null;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    date = simpleDateFormat.format(temp.getTime());
                    tv.setText(date);

                    if (status == 1 && dateCompare(temp, dateLimit) == 1) {
                        tv.setText("");
                        date = simpleDateFormat.format(dateLimit.getTime());

                    } else if (status == 0 && dateCompare(temp, dateLimit) == -1) {

                        tv.setText("");
                        date = simpleDateFormat.format(dateLimit.getTime());

                    } else {
                        dateCache.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
                    }


                }
            }
        });


    }

    public Calendar getDateMaxMin(final Calendar sourceDate, final Integer status, final Integer day, final Integer month, final Integer year) {
        Calendar temp = null;

        temp = Calendar.getInstance();
        temp.set(sourceDate.get(Calendar.YEAR), sourceDate.get(Calendar.MONTH), sourceDate.get(Calendar.DAY_OF_MONTH));

        if (status == 1)  // More than source
        {
            if (day != 0)
                temp.add(Calendar.DAY_OF_MONTH, day);
            if (month != 0)
                temp.add(Calendar.MONTH, month);
            if (year != 0)
                temp.add(Calendar.YEAR, year);
        } else { //less than source
            if (day != 0)
                temp.add(Calendar.DAY_OF_MONTH, (-1 * day));
            if (month != 0)
                temp.add(Calendar.MONTH, (-1 * month));
            if (year != 0)
                temp.add(Calendar.YEAR, (-1 * year));
        }

        return temp;
    }

}
