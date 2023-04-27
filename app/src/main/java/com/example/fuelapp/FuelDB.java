package com.example.fuelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class FuelDB {

    private final String DATABASE_NAME = "FuelDB.sqlite";
    private final String DATABASE_TABLE = "consumer_table";
    private final String DATABASE_TABLE2="add_vehicle";
    private final String DATABASE_TABLE3="filling_station";
    private final String DATABASE_TABLE4="admin_login";
    private final String DATABASE_TABLE5="admin_adding_filling_station";
    private final String DATABASE_TABLE6="admin_adding_filling_station_details";
    private final String DATABASE_TABLE7="manage_fuel";
    private final int DATABASE_VERSION = 1; //for tracking the upgrading requirement
    ContentValues cv, cv1, cv2, cv3, cv4, cv5, cv6, cv7;

    String uname="iamanadmin";
    String pw="ThisIsAdminLogin";

    public static final String KEY_NAME = "name"; //This underscore was required in earlier versions of android in order to add that column
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_DISTRICT = "district";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_VEHICLE_NAME="vehicle_or_machine_names";
    public static final String KEY_VEHICLE_ID="vehicle_or_machine_id";
    public static final String KEY_USERNAME="username";
    public static final String KEY_ID="registered_id";
    public static final String KEY_FILLING_STATION_NAME="registered_name";
    public static final String KEY_FUEL_TYPES="fuel_type";
    public static final String KEY_FUEL_AMOUNT="received_amount";
    public static final String KEY_PRICE_OF_1L="price_of_1l";
    public static final String KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH="amount";

    private DBHelper helper;//helper is the instance of DBHelper
    private final Context context;
    private SQLiteDatabase database;

    public FuelDB(Context context) {
        this.context = context;
    }//context of the MainActivity is being caught and is being assigned to the context object initialized within this class

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }//Creating the database

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_ADDRESS + " TEXT NOT NULL, " +
                    KEY_DISTRICT + " TEXT NOT NULL, "+
                    KEY_PASSWORD + " VARCHAR PRIMARY KEY);";
            db.execSQL(sqlCode);

            String sqlCode2 = "CREATE TABLE " + DATABASE_TABLE2 + " (" +
                    KEY_PASSWORD + " VARCHAR NOT NULL, " +
                    KEY_VEHICLE_NAME + " TEXT NOT NULL, " +
                    KEY_VEHICLE_ID + " TEXT PRIMARY KEY);";
            db.execSQL(sqlCode2);

            String sqlCode3 = "CREATE TABLE " + DATABASE_TABLE3 + " (" +
                    KEY_USERNAME + " TEXT NOT NULL, " +
                    KEY_DISTRICT + " TEXT NOT NULL, " +
                    KEY_ID + " VARCHAR PRIMARY KEY, " +
                    KEY_PASSWORD + " VARCHAR NOT NULL);";
            db.execSQL(sqlCode3);

            String sqlCode4 = "CREATE TABLE " + DATABASE_TABLE4 + " (" +
                    KEY_USERNAME + " TEXT NOT NULL, " +
                    KEY_PASSWORD + " VARCHAR PRIMARY KEY);";
            db.execSQL(sqlCode4);

            String sqlCode5 = "CREATE TABLE " + DATABASE_TABLE5 + " (" +
                    KEY_FILLING_STATION_NAME + " TEXT NOT NULL, " +
                    KEY_ID + " VARCHAR PRIMARY KEY);";
            db.execSQL(sqlCode5);

            String sqlCode6 = "CREATE TABLE " + DATABASE_TABLE6 + " (" +
                    KEY_FILLING_STATION_NAME + " TEXT NOT NULL, " +
                    KEY_FUEL_TYPES + " TEXT NOT NULL, " +
                    KEY_FUEL_AMOUNT + " INT NOT NULL, " +
                    KEY_PRICE_OF_1L + " INT NOT NULL);";
            db.execSQL(sqlCode6);

            String sqlCode7 = "CREATE TABLE " + DATABASE_TABLE7 + " (" +
                    KEY_VEHICLE_NAME + " VARCHAR NOT NULL, " +
                    KEY_FUEL_TYPES + " TEXT NOT NULL, " +
                    KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH + " INT NOT NULL);";
            db.execSQL(sqlCode7);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE4);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE5);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE6);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE7);
            onCreate(sqLiteDatabase);
        }
    }
    public  FuelDB open()
    {
        helper = new DBHelper(context);//Initializing the DBHelper instance using DBHelper class
        database = helper.getWritableDatabase();
        createEntry4();
        return this;//"this" object or the writable database is returning to the "open" method where it's being called
    }

    public void close()
    {
        helper.close();
    }

    public long createEntry(String name, String address, String district, String password)
    {
        cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_ADDRESS, address);
        cv.put(KEY_DISTRICT, district);
        cv.put(KEY_PASSWORD, password);

        return database.insert(DATABASE_TABLE, null, cv);
    }
    public long createEntry2(String password, String nameOfVehcle, String idOfVehicle)
    {
        cv1 = new ContentValues();

        cv1.put(KEY_PASSWORD, password);
        cv1.put(KEY_VEHICLE_NAME, nameOfVehcle);
        cv1.put(KEY_VEHICLE_ID, idOfVehicle);

        return database.insert(DATABASE_TABLE2, null, cv1);
    }
    public long createEntry3(String username, String dist, String registeredID, String pass)
    {
        cv2 = new ContentValues();

        cv2.put(KEY_USERNAME, username);
        cv2.put(KEY_DISTRICT, dist);
        cv2.put(KEY_ID, registeredID);
        cv2.put(KEY_PASSWORD, pass);

        return database.insert(DATABASE_TABLE3, null, cv2);
    }
    public String getUserAddress(String address) {
        String[] columns=new String[] {KEY_ADDRESS};
        String selection=KEY_ADDRESS + "=?";
        String[] selectionArgs={address};
        Cursor c=database.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
        String result="";

        int iAddress=c.getColumnIndex(KEY_ADDRESS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if ((c.getString(iAddress)).equals(address)) {
                result = address;
                break;
            }
        }
        c.close();
        return result;
    }
    public void removeUser(String address){
        String selection=KEY_ADDRESS + "=?";
        String[] selectionArgs={address};
        database.delete(DATABASE_TABLE, selection, selectionArgs);
    }
    public String getAdminCredentials(String username, String password) {
        String[] columns=new String[] {KEY_USERNAME, KEY_PASSWORD};
        Cursor c=database.query(DATABASE_TABLE4, columns, null, null, null, null, null);
        String result="";

        int iUsername=c.getColumnIndex(KEY_USERNAME);
        int iPassword=c.getColumnIndex(KEY_PASSWORD);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if (((c.getString(iUsername)).equals(username))&& ((c.getString(iPassword)).equals(password))) {
                result = "OK";
                break;
            }
        }
        c.close();
        return result;
    }
    public String getFillingStationCredentials(String username, String password) {
        String[] columns=new String[] {KEY_USERNAME, KEY_PASSWORD};
        Cursor c=database.query(DATABASE_TABLE3, columns, null, null, null, null, null);
        String result="";

        int iUsername=c.getColumnIndex(KEY_USERNAME);
        int iPassword=c.getColumnIndex(KEY_PASSWORD);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if (((c.getString(iUsername)).equals(username))&& ((c.getString(iPassword)).equals(password))) {
                result = "OK";
                break;
            }
        }
        c.close();
        return result;
    }
    public String getConsumerCredentials(String username, String password) {
        String[] columns=new String[] {KEY_NAME, KEY_PASSWORD};
        Cursor c=database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result="";

        int iUsername=c.getColumnIndex(KEY_NAME);
        int iPassword=c.getColumnIndex(KEY_PASSWORD);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if (((c.getString(iUsername)).equals(username))&& ((c.getString(iPassword)).equals(password))) {
                result = "OK";
                break;
            }
        }
        c.close();
        return result;
    }
    public String getConsumerDistrict(String username, String password) {
        String[] columns=new String[] {KEY_NAME, KEY_PASSWORD, KEY_DISTRICT};
        Cursor c=database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result="";

        int iUsername=c.getColumnIndex(KEY_NAME);
        int iPassword=c.getColumnIndex(KEY_PASSWORD);
        int iDistrict=c.getColumnIndex(KEY_DISTRICT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if (((c.getString(iUsername)).equals(username))&& ((c.getString(iPassword)).equals(password))) {
                result = c.getString(iDistrict);
                break;
            }
        }
        c.close();
        return result;
    }
    public void createEntry4(){
        cv3 = new ContentValues();

        cv3.put(KEY_USERNAME, uname);
        cv3.put(KEY_PASSWORD, pw);

        database.insert(DATABASE_TABLE4, null, cv3);
    }
    public long createEntry5(String name, String ID)
    {
        cv4 = new ContentValues();

        cv4.put(KEY_FILLING_STATION_NAME, name);
        cv4.put(KEY_ID, ID);

        return database.insert(DATABASE_TABLE5, null, cv4);
    }
    public String getRightFillingStation(String name, String ID) {
        String[] columns=new String[] {KEY_FILLING_STATION_NAME, KEY_ID};
        String selection=KEY_FILLING_STATION_NAME + "=?" +" AND " +KEY_ID + "=? ";
        String[] selectionArgs={name, ID};
        Cursor c=database.query(DATABASE_TABLE5, columns, selection, selectionArgs, null, null, null);
        String result="";

        int iName=c.getColumnIndex(KEY_FILLING_STATION_NAME);
        int iID=c.getColumnIndex(KEY_ID);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                result="OK";
        }
        c.close();
        return result;
    }
    public String[] getFillingStation(String name) {
        String[] columns=new String[] {KEY_FILLING_STATION_NAME, KEY_ID};
        String selection=KEY_FILLING_STATION_NAME + "=?";
        String[] selectionArgs={name};
        Cursor c=database.query(DATABASE_TABLE5, columns, selection, selectionArgs, null, null, null);
        String result[]=new String[2];

        int iName=c.getColumnIndex(KEY_FILLING_STATION_NAME);
        int iID=c.getColumnIndex(KEY_ID);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                result[0]=c.getString(iName);
                result[1]=c.getString(iID);
                break;
        }
        c.close();
        return result;
    }
    public String[] getFillingStationFuelDetails(String name, String fuelType) {
        String[] columns=new String[] {KEY_FUEL_AMOUNT, KEY_PRICE_OF_1L};
        String selection=KEY_FILLING_STATION_NAME + "=?" +" AND " +KEY_FUEL_TYPES + "=? ";
        String[] selectionArgs=new String[] {name, fuelType};
        Cursor c=database.query(DATABASE_TABLE6, columns, selection, selectionArgs, null, null, null);
        String result[]=new String[2];

        int ifAmount=c.getColumnIndex(KEY_FUEL_AMOUNT);
        int iPrice=c.getColumnIndex(KEY_PRICE_OF_1L);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                result[0]=c.getString(ifAmount);
                result[1]=c.getString(iPrice);
                break;
        }
        c.close();
        return result;
    }
    public String getFillingStationDistrict(String district){
        String[] columns=new String[] {KEY_USERNAME};
        String selection=KEY_DISTRICT + "=?";
        String[] selectionArgs={district};
        Cursor c=database.query(DATABASE_TABLE3, columns, selection, selectionArgs, null, null, null);
        String results="";
        int i=-1;

        int iUsername=c.getColumnIndex(KEY_USERNAME);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            i++;
            results+=c.getString(iUsername) + ",";
        }
        c.close();
        return results;
    }
    public String[] loadRegisterdFillingStations(){

        String[] columns=new String[] {KEY_USERNAME};
        Cursor c=database.query(DATABASE_TABLE3, columns, null, null, null, null, null);

        long count = DatabaseUtils.queryNumEntries(database, DATABASE_TABLE3);

        String[] listOfStations = new String[(int) count];

        int i=-1;

        int iUsername=c.getColumnIndex(KEY_USERNAME);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            i++;
            listOfStations[i]=c.getString(iUsername);
        }
        c.close();
        return listOfStations;
    }
    public long createEntry6(String name, String type, int amount, int price)
    {
        long rec = 0;
        
        cv5 = new ContentValues();

        String[] columns=new String[] {KEY_FUEL_AMOUNT};
        String selection=KEY_FILLING_STATION_NAME + "=?" +" AND " +KEY_FUEL_TYPES + "=? ";
        String[] selectionArgs=new String[]{name, type};
        Cursor c=database.query(DATABASE_TABLE6, columns, selection, selectionArgs, null, null, null);
        int results=0;

        int iAmount=c.getColumnIndex(KEY_FUEL_AMOUNT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            results=c.getInt(iAmount);
        }
        c.close();

        int newAmount=0;
        if(results!=0){
            newAmount=results+amount;
            cv5.put(KEY_FUEL_AMOUNT, newAmount);

            database.update(DATABASE_TABLE6, cv5, selection, selectionArgs);
        }else{
            cv5.put(KEY_FILLING_STATION_NAME, name);
            cv5.put(KEY_FUEL_TYPES, type);
            cv5.put(KEY_FUEL_AMOUNT, amount);
            cv5.put(KEY_PRICE_OF_1L, price);
            
            rec=database.insert(DATABASE_TABLE6, null, cv5);
        }
        return rec;
    }
    public long createEntry7(String name, String type, int amount)
    {
        long rec = 0;

        cv6 = new ContentValues();

        String[] columns=new String[] {KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH};
        String selection=KEY_VEHICLE_NAME + "=?" +" AND " +KEY_FUEL_TYPES + "=? ";
        String[] selectionArgs=new String[]{name, type};
        Cursor c=database.query(DATABASE_TABLE7, columns, selection, selectionArgs, null, null, null);
        int results=0;

        int iAmount=c.getColumnIndex(KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            results=c.getInt(iAmount);
        }
        c.close();

        if(results!=0){
            cv6.put(KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH, amount);

            database.update(DATABASE_TABLE7, cv6, selection, selectionArgs);
        }else{
            cv6.put(KEY_VEHICLE_NAME, name);
            cv6.put(KEY_FUEL_TYPES, type);
            cv6.put(KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH, amount);

            rec=database.insert(DATABASE_TABLE7, null, cv6);
        }
        return rec;
    }
    public String[] loadVehicles(String password){
        String[] columns=new String[] {KEY_VEHICLE_NAME};
        String selection=KEY_PASSWORD + "=? ";
        String[] selectionArgs=new String[]{password};
        Cursor c=database.query(DATABASE_TABLE2, columns, selection, selectionArgs, null, null, null);

        int i=-1;

        long count = DatabaseUtils.queryNumEntries(database, DATABASE_TABLE2, selection, selectionArgs);

        String numberOfVehicles[] = new String[(int) count];

        int iVehicle=c.getColumnIndex(KEY_VEHICLE_NAME);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            i++;
            numberOfVehicles[i]=c.getString(iVehicle);
        }
        c.close();

        return numberOfVehicles;
    }
    public int allocatedFuelAmount(String item){
        String[] columns=new String[] {KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH};
        String selection=KEY_VEHICLE_NAME + "=? ";
        String[] selectionArgs=new String[]{item};
        Cursor c=database.query(DATABASE_TABLE7, columns, selection, selectionArgs, null, null, null);

        int result=0;

        int iAmount=c.getColumnIndex(KEY_REQUIRED_FUEL_AMOUNT_PER_MONTH);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result=c.getInt(iAmount);
        }
        c.close();

        return result;
    }
    public String allocatedFuelType(String item){
        String[] columns=new String[] {KEY_FUEL_TYPES};
        String selection=KEY_VEHICLE_NAME + "=? ";
        String[] selectionArgs=new String[]{item};
        Cursor c=database.query(DATABASE_TABLE7, columns, selection, selectionArgs, null, null, null);

        String result="";

        int iFuelType=c.getColumnIndex(KEY_FUEL_TYPES);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result=c.getString(iFuelType);
        }
        c.close();

        return result;
    }
    public int existingFuelAmount(String name, String type){
        String[] columns=new String[] {KEY_FUEL_AMOUNT};
        String selection=KEY_FILLING_STATION_NAME + "=?" +" AND " +KEY_FUEL_TYPES + "=? ";
        String[] selectionArgs=new String[]{name, type};
        Cursor c=database.query(DATABASE_TABLE6, columns, selection, selectionArgs, null, null, null);

        int result=0;

        int iAmount=c.getColumnIndex(KEY_FUEL_AMOUNT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result=c.getInt(iAmount);
        }
        c.close();

        return result;
    }
    public long updateExistingFuelAmount(String name, String type, int amount){
        long rec=0;

        cv7 = new ContentValues();

        String[] columns=new String[] {KEY_FUEL_AMOUNT};
        String selection=KEY_FILLING_STATION_NAME + "=?" +" AND " +KEY_FUEL_TYPES + "=? ";
        String[] selectionArgs=new String[]{name, type};
        Cursor c=database.query(DATABASE_TABLE6, columns, selection, selectionArgs, null, null, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            cv7.put(KEY_FUEL_AMOUNT, amount);

            database.update(DATABASE_TABLE6, cv7, selection, selectionArgs);

            rec=1;
        }
        c.close();
        return rec;
    }
}
