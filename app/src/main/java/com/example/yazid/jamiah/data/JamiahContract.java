package com.example.yazid.jamiah.data;

import android.provider.BaseColumns;

/**
 * Created by yazid on 4/6/17.
 */

public class JamiahContract  {

    private JamiahContract(){}

    public static final class JamiahEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "jamiahs";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_JAMIAH_AMOUNT = "amount";
        public final static String COLUMN_JAMIAH_MONTHS = "months";
        public final static String COLUMN_JAMIAH_PERSONS = "persons";
        public final static String COLUMN_JAMIAH_START_DATE = "start_date";
        public final static String COLUMN_JAMIAH_end_DATE = "end_date";

    }
}
