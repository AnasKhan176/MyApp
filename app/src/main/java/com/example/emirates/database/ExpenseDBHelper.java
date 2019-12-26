package com.example.emirates.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDBHelper extends SQLiteOpenHelper {

    private static ExpenseDBHelper instance;

    private static final String SQL_CREATE_EXPENSE_DATA = "CREATE TABLE " + DBSetting.DBEntry.Table_expense
            + "("
            + DBSetting.DBEntry.Column_e_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBSetting.DBEntry.Column_e_date + " TEXT,"
            + DBSetting.DBEntry.Column_e_ptype + " TEXT,"
            + DBSetting.DBEntry.Column_e_pval + " TEXT,"
            + DBSetting.DBEntry.Column_e_from + " TEXT,"
            + DBSetting.DBEntry.Column_e_to + " TEXT,"
            + DBSetting.DBEntry.Column_e_gross + " TEXT"
            + ")";

    public ExpenseDBHelper(Context context) {
        super(context, DBSetting.DB_NAME, null, DBSetting.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXPENSE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBSetting.DBEntry.Table_expense);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static synchronized ExpenseDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ExpenseDBHelper(context);
        }
        return instance;
    }

    public boolean expenseEntry(String date, String pmode, String pval, String from, String to, String amt) {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();
        long res = -1;
        ContentValues cVal = new ContentValues();
        try {
            db.beginTransaction();
            cVal.put(DBSetting.DBEntry.Column_e_date, date);
            cVal.put(DBSetting.DBEntry.Column_e_ptype, pmode);
            cVal.put(DBSetting.DBEntry.Column_e_pval, pval);
            cVal.put(DBSetting.DBEntry.Column_e_from, from);
            cVal.put(DBSetting.DBEntry.Column_e_to, to);
            cVal.put(DBSetting.DBEntry.Column_e_gross, amt);
            res = db.insert(DBSetting.DBEntry.Table_expense, null, cVal);
            if (res == -1)
                status = false;
            else
                status = true;
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
        return status;
    }

    public int getDataCount() {
        int count = 0;
        String selectQuery = "";

        selectQuery = "SELECT * FROM " + DBSetting.DBEntry.Table_expense;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            count = cursor.getCount();
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
