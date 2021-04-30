package com.example.horseriding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    //client
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "horseRidingManager";
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "clientId";
    private static final String KEY_NAME = "fName";
    private static final String KEY_LAST_NAME = "lName";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_IDENTITY = "identityDoc";
    private static final String KEY_MAIL = "clientEmail";
    private static final String KEY_PASSWORD = "passwd";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_PH_NO = "clientPhone";
    //user
    private static final String KEY_UID = "userId";
    private static final String KEY_UEMAIL = "userEmail";
    private static final String KEY_UPASSWD = "userPasswd";
    private static final String KEY_UFNAME = "userFname";
    private static final String KEY_ULNAME = "userLname";
    private static final String KEY_UDESCRIPTION = "description";
    private static final String KEY_UTYPE = "userType";
    private static final String KEY_UPHOTO = "userphoto";
    private static final String KEY_UPHONE = "userPhone";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CLIENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+KEY_LAST_NAME+" TEXT,"+KEY_PHOTO+" TEXT,"+KEY_IDENTITY+" TEXT,"+KEY_MAIL+" TEXT,"
                +KEY_PASSWORD+" TEXT,"+KEY_NOTES+" TEXT,"
                + KEY_PH_NO + " TEXT" + ")";*/
        // CREATE_CONTACTS_TABLE="CREATE TABLE myclients (clientId INTEGER PRIMARY KEY,fName TEXT,lName TEXT)";
        String CREATE_USER_TABLE="CREATE TABLE "+TABLE_USERS+"("+KEY_UID+" INETEGER PRIMARY KEY,"+KEY_UEMAIL+" TEXT,"+KEY_UPASSWD+" TEXT,"+KEY_UFNAME+" TEXT,"
                +KEY_ULNAME+" TEXT,"+KEY_UDESCRIPTION+" TEXT,"+KEY_UTYPE+" TEXT,"+KEY_UPHOTO+" TEXT,"+KEY_UPHONE+" TEXT)";
        String CREATE_CLIENT_TABLE="CREATE TABLE "+TABLE_CLIENTS+"("+KEY_ID+" INETEGER PRIMARY KEY,"+KEY_NAME+" TEXT,"+KEY_LAST_NAME+" TEXT,"+KEY_PHOTO+" TEXT,"
                +KEY_IDENTITY+" TEXT,"+KEY_MAIL+" TEXT,"+KEY_PASSWORD+" TEXT,"+KEY_PH_NO+" TEXT,"+KEY_NOTES+" TEXT)";


        db.execSQL(CREATE_CLIENT_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new client
    void addClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_ID,client.getClientId());
        values.put(KEY_NAME, client.getfName()); // Contact Name
        values.put(KEY_LAST_NAME, client.getlName()); // Contact Phone
        values.put(KEY_PHOTO, client.getPhoto()); // Contact Phone
        values.put(KEY_IDENTITY, client.getIdentityDoc()); // Contact Phone
        values.put(KEY_MAIL, client.getClientEmail()); // Contact Phone
        values.put(KEY_PASSWORD, client.getPasswd()); // Contact Phone
        values.put(KEY_NOTES, client.getNotes()); // Contact Phone
        values.put(KEY_PH_NO, client.getClientPhone()); // Contact Phone
         // Contact Phone

        // Inserting Row
        db.insert(TABLE_CLIENTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_UID,user.getUserId());
        values.put(KEY_UFNAME, user.getUserFname()); // Contact Name
        values.put(KEY_ULNAME, user.getUserLname()); // Contact Phone
        values.put(KEY_UPHOTO, user.getUserphoto()); // Contact Phone
        values.put(KEY_UEMAIL, user.getUserEmail()); // Contact Phone
        values.put(KEY_UPASSWD, user.getUserPasswd()); // Contact Phone
        values.put(KEY_UDESCRIPTION, user.getDescription()); // Contact Phone
        values.put(KEY_UTYPE, user.getUserType()); // Contact Phone
        values.put(KEY_UPHONE, user.getUserPhone()); // Contact Phone
        // Contact Phone

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Client getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLIENTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_LAST_NAME,KEY_PHOTO,KEY_IDENTITY,KEY_MAIL,KEY_PASSWORD,KEY_PH_NO,KEY_NOTES }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Client client = new Client(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8));
        // return contact
        return client;
    }

    // code to get all contacts in a list view
    public List<Client> getAllContacts() {
        List<Client> contactList = new ArrayList<Client>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Client contact = new Client();
                contact.setClientId(Integer.parseInt(cursor.getString(0)));
                contact.setfName(cursor.getString(1));
                contact.setlName(cursor.getString(2));
                contact.setPhoto(cursor.getString(3));
                contact.setIdentityDoc(cursor.getString(4));
                contact.setClientEmail(cursor.getString(5));
                contact.setPasswd(cursor.getString(6));
                contact.setClientPhone(cursor.getString(7));
                contact.setNotes(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, client.getfName());
        values.put(KEY_LAST_NAME, client.getfName());
        values.put(KEY_PHOTO, client.getfName());
        values.put(KEY_IDENTITY, client.getfName());
        values.put(KEY_MAIL, client.getfName());
        values.put(KEY_PASSWORD, client.getfName());
        values.put(KEY_PH_NO, client.getfName());
        values.put(KEY_NOTES, client.getClientPhone());

        // updating row
        return db.update(TABLE_CLIENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(client.getClientId()) });
    }

    // Deleting single contact
    public void deleteContact(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(client.getClientId()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}