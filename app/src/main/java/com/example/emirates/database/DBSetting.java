package com.example.emirates.database;

import android.provider.BaseColumns;

public final class DBSetting {

    public static final String DB_NAME = "expense.db";
    public static final int DB_VERSION = 1;


    public static abstract class DBEntry implements BaseColumns {

        public static final String Table_expense = "expense";
        public static final String Column_e_id = "e_id";
        public static final String Column_e_date = "e_date";
        public static final String Column_e_ptype = "e_ptype";
        public static final String Column_e_pval = "e_pval";
        public static final String Column_e_from = "e_from";
        public static final String Column_e_to = "e_to";
        public static final String Column_e_gross = "e_gross";


    }
}
