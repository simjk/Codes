package e.j_enn.repa.control;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.List;
import java.util.ArrayList;

import android.database.Cursor;

import e.j_enn.repa.entity.User;

public class UserDBManager extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 2;

    //Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    //User table name
    private static final String TABLE_USER = "user";


    // User Table Columns names
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_SALUTATION = "salutation";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_SALUTATION + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public UserDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop user table if exist
        db.execSQL(DROP_USER_TABLE);

        //Create tables again
        onCreate(db);
    }

    //Create user record
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_SALUTATION, user.getSalutation());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getPhone());

        //Insert Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    //Fetch all user and return the list user records
    public List<User> getAllUser() {
        String[] columns = {
                COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_SALUTATION, COLUMN_NAME, COLUMN_PHONE
        };

        //Sort orders
        String sortOrder = COLUMN_EMAIL + "ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Query the user table
        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                user.setSalutation(cursor.getString(cursor.getColumnIndex(COLUMN_SALUTATION)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        //return user list
        return userList;
    }

    //Update user record
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getPhone());

        // updating row
        db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
        db.close();
    }

    //Delete user record
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
        db.close();
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_EMAIL + " = ?";

        // selection arguments
        String[] selectionArgs = {email};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
