package e.j_enn.repa.control;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import e.j_enn.repa.entity.Property;

public class SearchDBManager extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 2;

    //Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    //User table name
    private static final String TABLE_USER = "property";

    // User Table Columns names
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_HOUSING_UNIT = "housing_unit";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TOWN_AREA = "town_area";
    private static final String COLUMN_PROPERTY_TYPE = "property_type";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_USER = "user";
    private static final String COLUMN_TENTURE = "tenure";
    private static final String COLUMN_FLOOR_AREA = "floor_area";
    private static final String COLUMN_NO_OF_BED = "no_of_bed";
    private static final String COLUMN_NO_OF_BATH = "no_of_bath";
    private static final String COLUMN_DESCRIPTION = "description";

    byte[] image;

    private String CREATE_PROPERTY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_IMAGE + " BLOB,"
            + COLUMN_HOUSING_UNIT + " TEXT,"
            + COLUMN_TOWN_AREA + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_PROPERTY_TYPE + " TEXT,"
            + COLUMN_PRICE + " TEXT,"
            + COLUMN_TENTURE + " TEXT,"
            + COLUMN_FLOOR_AREA + " TEXT,"
            + COLUMN_NO_OF_BATH + " TEXT,"
            + COLUMN_NO_OF_BED + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_USER + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public SearchDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROPERTY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);

        if (db.getVersion() < DATABASE_VERSION) {
            db.setVersion(DATABASE_VERSION);
        }
        onCreate(db);
    }

    public void addProperty(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSING_UNIT, property.getHousing_unit());
        values.put(COLUMN_TOWN_AREA, property.getTown_area());
        values.put(COLUMN_ADDRESS, property.getAddress());
        values.put(COLUMN_PROPERTY_TYPE, property.getProperty_type());
        values.put(COLUMN_PRICE, property.getPrice());
        values.put(COLUMN_TENTURE, property.getTenure());
        values.put(COLUMN_FLOOR_AREA, property.getFloor_area());
        values.put(COLUMN_NO_OF_BATH, property.getNo_of_bath());
        values.put(COLUMN_NO_OF_BED, property.getNo_of_bed());
        values.put(COLUMN_DESCRIPTION, property.getDescription());
        values.put(COLUMN_USER, property.getUser());
        values.put(COLUMN_IMAGE, property.getProperty_Image());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void updateProperty(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSING_UNIT, property.getHousing_unit());
        values.put(COLUMN_TOWN_AREA, property.getTown_area());
        values.put(COLUMN_ADDRESS, property.getAddress());
        values.put(COLUMN_PROPERTY_TYPE, property.getProperty_type());
        values.put(COLUMN_PRICE, property.getPrice());
        values.put(COLUMN_TENTURE, property.getTenure());
        values.put(COLUMN_FLOOR_AREA, property.getFloor_area());
        values.put(COLUMN_NO_OF_BATH, property.getNo_of_bath());
        values.put(COLUMN_NO_OF_BED, property.getNo_of_bed());
        values.put(COLUMN_DESCRIPTION, property.getDescription());
        values.put(COLUMN_USER, property.getUser());
        values.put(COLUMN_IMAGE, property.getProperty_Image());

        // updating row
        db.update(TABLE_USER, values, COLUMN_HOUSING_UNIT + " = ?",
                new String[]{String.valueOf(property.getHousing_unit())});
        db.close();
    }

    public void deleteUser(Property property) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_HOUSING_UNIT + " = ?",
                new String[]{String.valueOf(property.getHousing_unit())});
        db.close();
    }

    public boolean checkProperty(String housing_Unit, String town_Area, String property_Type, String sell_Price) {
        // number of columns to fetch
        String[] columns = {COLUMN_HOUSING_UNIT, COLUMN_ADDRESS, COLUMN_TOWN_AREA, COLUMN_PROPERTY_TYPE, COLUMN_PRICE
                , COLUMN_TENTURE, COLUMN_FLOOR_AREA, COLUMN_NO_OF_BED, COLUMN_NO_OF_BATH, COLUMN_DESCRIPTION, COLUMN_USER};

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_HOUSING_UNIT + " = ?" + " AND " + COLUMN_TOWN_AREA + " = ?" + " AND " +
                COLUMN_PROPERTY_TYPE + " = ?" + " AND " + COLUMN_PRICE + " > ? ";

        //selection args
        String[] selectionArgs = {housing_Unit, town_Area, property_Type, sell_Price};

        //query user table with condition
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkSearchProperty(String text) {
        // number of columns to fetch
        String[] columns = {COLUMN_HOUSING_UNIT, COLUMN_ADDRESS, COLUMN_TOWN_AREA, COLUMN_PROPERTY_TYPE, COLUMN_PRICE
                , COLUMN_TENTURE, COLUMN_FLOOR_AREA, COLUMN_NO_OF_BED, COLUMN_NO_OF_BATH, COLUMN_DESCRIPTION, COLUMN_USER};

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_HOUSING_UNIT + " LIKE ?" + " OR " + COLUMN_TOWN_AREA + " LIKE ? " + " OR " +
                COLUMN_PROPERTY_TYPE + " LIKE ?";

        //selection args
        String[] selectionArgs = {'%' + text + '%', '%' + text + '%', '%' + text + '%'};

        //query user table with condition
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public byte[] getSelectedImage(Bundle bundle) {
        String[] columns = {COLUMN_IMAGE};

        SQLiteDatabase db = this.getReadableDatabase();
        byte[] propertyList = new byte[0];

        String selection = COLUMN_HOUSING_UNIT + " = ?" + " AND " +
                COLUMN_ADDRESS + " = ?" + " AND " +
                COLUMN_TOWN_AREA + " = ?" + " AND " +
                COLUMN_PROPERTY_TYPE + " = ?" + " AND " +
                COLUMN_PRICE + " = ? " + " AND " +
                COLUMN_TENTURE + " = ?" + " AND " +
                COLUMN_FLOOR_AREA + " = ? " + " AND " +
                COLUMN_NO_OF_BATH + " = ?" + " AND " +
                COLUMN_NO_OF_BED + " = ?" + " AND " +
                COLUMN_DESCRIPTION + " = ?" + " AND " +
                COLUMN_USER + " = ?";

        String housingUnit = bundle.get("housingUnit").toString();
        String address = bundle.get("address").toString();
        String townArea = bundle.get("townArea").toString();
        String propertyType = bundle.get("propertyType").toString();
        String price = bundle.get("price").toString();
        String tenure = bundle.get("tenure").toString();
        String floorArea = bundle.get("floorArea").toString();
        String bed = bundle.get("bed").toString();
        String bath = bundle.get("bath").toString();
        String description = bundle.get("description").toString();
        String user = bundle.get("user").toString();

        String[] selectionArgs = {housingUnit, address, townArea, propertyType, price, tenure, floorArea, bed, bath, description, user};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            propertyList = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));  
        }

        cursor.close();
        db.close();
        return propertyList;
    }

    public List<Property> getSearchProperty(String text) {
        List<Property> propertyList = new ArrayList<Property>();
        String[] columns = {COLUMN_IMAGE, COLUMN_HOUSING_UNIT, COLUMN_TOWN_AREA, COLUMN_ADDRESS, COLUMN_PROPERTY_TYPE, COLUMN_PRICE
                , COLUMN_TENTURE, COLUMN_FLOOR_AREA, COLUMN_NO_OF_BED, COLUMN_NO_OF_BATH, COLUMN_DESCRIPTION, COLUMN_USER};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_HOUSING_UNIT + " LIKE ?" + " OR " + COLUMN_TOWN_AREA + " LIKE ? " + " OR " +
                COLUMN_PROPERTY_TYPE + " LIKE ?";

        //selection args
        String[] selectionArgs = {'%' + text + '%', '%' + text + '%', '%' + text + '%'};
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Property property = new Property();
                property.setHousing_unit(cursor.getString(cursor.getColumnIndex(COLUMN_HOUSING_UNIT)));
                property.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                property.setTown_area(cursor.getString(cursor.getColumnIndex(COLUMN_TOWN_AREA)));
                property.setFloor_area(cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR_AREA)));
                property.setTenure(cursor.getString(cursor.getColumnIndex(COLUMN_TENTURE)));
                property.setNo_of_bed(cursor.getString(cursor.getColumnIndex(COLUMN_NO_OF_BED)));
                property.setNo_of_bath(cursor.getString(cursor.getColumnIndex(COLUMN_NO_OF_BATH)));
                property.setProperty_type(cursor.getString(cursor.getColumnIndex(COLUMN_PROPERTY_TYPE)));
                property.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                property.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                property.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER)));
                property.setProperty_Image(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));

                propertyList.add(property);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return propertyList;
    }

    public List<Property> getSelectedProperty(String housing_Unit, String town_Area, String property_Type, String sell_Price) {
        // number of columns to fetch
        String[] columns = {COLUMN_IMAGE, COLUMN_HOUSING_UNIT, COLUMN_TOWN_AREA, COLUMN_ADDRESS, COLUMN_PROPERTY_TYPE, COLUMN_PRICE
                , COLUMN_TENTURE, COLUMN_FLOOR_AREA, COLUMN_NO_OF_BED, COLUMN_NO_OF_BATH, COLUMN_DESCRIPTION, COLUMN_USER};

        List<Property> propertyList = new ArrayList<Property>();

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_HOUSING_UNIT + " = ?" + " AND " + COLUMN_TOWN_AREA + " = ?" + " AND " +
                COLUMN_PROPERTY_TYPE + " = ?" + " AND " + COLUMN_PRICE + " > ? ";

        //selection args
        String[] selectionArgs = {housing_Unit, town_Area, property_Type, sell_Price};

        //query user table with condition
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Property property = new Property();
                property.setHousing_unit(cursor.getString(cursor.getColumnIndex(COLUMN_HOUSING_UNIT)));
                property.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                property.setTown_area(cursor.getString(cursor.getColumnIndex(COLUMN_TOWN_AREA)));
                property.setFloor_area(cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR_AREA)));
                property.setTenure(cursor.getString(cursor.getColumnIndex(COLUMN_TENTURE)));
                property.setNo_of_bed(cursor.getString(cursor.getColumnIndex(COLUMN_NO_OF_BED)));
                property.setNo_of_bath(cursor.getString(cursor.getColumnIndex(COLUMN_NO_OF_BATH)));
                property.setProperty_type(cursor.getString(cursor.getColumnIndex(COLUMN_PROPERTY_TYPE)));
                property.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                property.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                property.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER)));
                property.setProperty_Image(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));

                propertyList.add(property);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return propertyList;
    }
}
