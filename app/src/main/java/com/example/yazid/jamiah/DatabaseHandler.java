//package com.example.yazid.jamiah;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by yazid on 3/3/17.
// */
//
//public class DatabaseHandler extends SQLiteOpenHelper {
//
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 2;
//
//    // Database Name
//    private static final String DATABASE_NAME = "JamiahManager";
//
//    // Contacts table name
//    private static final String TABLE_JAMIAH = "Jamiah";
//
//    // Contacts Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_AMOUNTS = "Amount";
//    private static final String KEY_MONTHS = "Months";
//    private static final String KEY_NUMBER_OF_PERSONS = "NumberOfPersons";
//    private static final String KEY_START_DATE = "startDate";
//    private static final String KEY_END_DATE = "endDate";
//
//    public DatabaseHandler(Context context) {
//        //super(context, name, factory, version);
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//
///*
//      String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
//                + KEY_PH_NO + " TEXT" + ")";
//        db.execSQL(CREATE_CONTACTS_TABLE);
// */
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_JAMIAH_TABLE = "CREATE TABLE " + TABLE_JAMIAH + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AMOUNTS + " INTEGER,"
//                + KEY_MONTHS + " INTEGER," + KEY_NUMBER_OF_PERSONS + " INTEGER,"
//                + KEY_START_DATE + " TEXT,"+ KEY_END_DATE + " TEXT"+ ");";
//        db.execSQL(CREATE_JAMIAH_TABLE);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JAMIAH);
//        // Create tables again
//        onCreate(db);
//    }
//
//
//    /*
//        // Adding new contact
//    void addContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName()); // Contact Name
//        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
//
//        // Inserting Row
//        db.insert(TABLE_CONTACTS, null, values);
//        db.close(); // Closing database connection
//    }
//     */
//    //add new Jamiah
//    public void addJamiah(Jamiah jamiah) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_AMOUNTS, jamiah.getAmount()); // Contact Name
//        values.put(KEY_MONTHS, jamiah.getMonths()); // Contact Phone Number
//        values.put(KEY_NUMBER_OF_PERSONS, jamiah.getNumberOfPersons());
//        values.put(KEY_START_DATE, jamiah.getStartDate());
//        values.put(KEY_END_DATE, jamiah.getEndDate());
//
//        // Inserting Row
//        db.insert(TABLE_JAMIAH, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting single contact
//    public Jamiah getJamiah(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_JAMIAH, new String[] { KEY_ID,
//                        KEY_AMOUNTS, KEY_MONTHS, KEY_NUMBER_OF_PERSONS,KEY_START_DATE,KEY_END_DATE}, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//      //  SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
//      //  String endDate = formatter.format(cursor.getString(5));
//
//        Jamiah jamiah = new Jamiah(Integer.parseInt(cursor.getString(0)),
//                Integer.parseInt(cursor.getString(1)) , Integer.parseInt(cursor.getString(2)),
//                Integer.parseInt(cursor.getString(3)),cursor.getString(4), cursor.getString(5));
//
//        // return contact
//        return jamiah;
//    }
//
//    public List<Jamiah> getAllJamiahs()
//    {
//        List<Jamiah> JamiahList = new ArrayList<Jamiah>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_JAMIAH;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Jamiah jamiah = new Jamiah();
//
//                jamiah.setId(Integer.parseInt(cursor.getString(0)));
//                jamiah.setAmount(Integer.parseInt(cursor.getString(1)));
//                jamiah.setMonths(Integer.parseInt(cursor.getString(2)));
//                jamiah.setNumberOfPersons(Integer.parseInt(cursor.getString(3)));
//                jamiah.setStartDate(cursor.getString(4));
//                jamiah.setEndDate(cursor.getString(5));
//
//                // Adding contact to list
//                JamiahList.add(jamiah);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return JamiahList;
//    }
//
//    // Getting contacts Count
//    public int getJamiahsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_JAMIAH;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
//
//    // Updating single contact
//    public int updateContact(Jamiah jamiah) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_AMOUNTS, jamiah.getAmount());
//        values.put(KEY_MONTHS, jamiah.getMonths());
//        values.put(KEY_NUMBER_OF_PERSONS, jamiah.getNumberOfPersons());
//        values.put(KEY_START_DATE, jamiah.getStartDate());
//        values.put(KEY_END_DATE, jamiah.getEndDate());
//
//        // updating row
//        return db.update(TABLE_JAMIAH, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(jamiah.getId()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Jamiah jamiah) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_JAMIAH, KEY_ID + " = ?",
//                new String[] { String.valueOf(jamiah.getId()) });
//        db.close();
//    }
//
//}
