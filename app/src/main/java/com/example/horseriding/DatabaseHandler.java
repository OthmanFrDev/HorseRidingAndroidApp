package com.example.horseriding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
//seance


    private static final String TABLE_SEANCES = "seancestable";
    private static final String KEY_SID = "seanceId";
    private static final String KEY_GID = "seanceGrpId";
    private static final String KEY_MONITOR = "monitorId";
    private static final String KEY_START = "startDate";
    private static final String KEY_DURATION = "durationMinut";
    private static final String KEY_ISDone = "isDone";
    private static final String KEY_PID = "paymentId";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_CLIENTID = "clientId";

    //tasks
    private static final String TABLE_TASKS = "seancetable      ";
    private static final String KEY_TID = "taskId";
    private static final String KEY_TSTART = "startDate";
    private static final String KEY_TDURATION = "durationMinut";
    private static final String KEY_TTITLE = "title";
    private static final String KEY_TDETAIL = "detail";
    private static final String KEY_TISDone = "isDone";
    private static final String KEY_TUSERFK = "userFk";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + KEY_UID + " INTEGER PRIMARY KEY," + KEY_UEMAIL + " TEXT," + KEY_UPASSWD + " TEXT," + KEY_UFNAME + " TEXT,"
                + KEY_ULNAME + " TEXT," + KEY_UDESCRIPTION + " TEXT," + KEY_UTYPE + " TEXT," + KEY_UPHOTO + " TEXT," + KEY_UPHONE + " TEXT)";
        String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LAST_NAME + " TEXT," + KEY_PHOTO + " TEXT,"
                + KEY_IDENTITY + " TEXT," + KEY_MAIL + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_PH_NO + " TEXT," + KEY_NOTES + " TEXT)";
        String CREATE_SEANCE_TABLE = "CREATE TABLE " + TABLE_SEANCES +
                "(" + KEY_START + " TEXT , " +
                KEY_DURATION + " INTEGER , " +
                KEY_SID + " INTEGER PRIMARY KEY , " +
                KEY_CLIENTID + " INTEGER , " +
                KEY_MONITOR + " INTEGER , " +
                KEY_COMMENTS + " TEXT)";

        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS +
                "(" + KEY_TID + " INTEGER PRIMARY KEY , " +
                KEY_TSTART + " TEXT , " +
                KEY_TDURATION + " INTEGER  , " +
                KEY_TTITLE + " TEXT , " +
                KEY_TDETAIL + " TEXT , " +
                KEY_TISDone + " TEXT , " +
                KEY_TUSERFK + " INTEGER )";
        String CREATE_NOTES_TABLE = "CREATE TABLE NOTES ( id INTEGER PRIMARY KEY AUTOINCREMENT , notes TEXT , date TEXT )";

        db.execSQL(CREATE_CLIENT_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SEANCE_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//       // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEANCES);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
//
//        // Create tables again

    }

    public void saveSeance(Seance seance) {

        ContentValues ligne = new ContentValues();
        ligne.put(KEY_SID, seance.getSeanceId());
        ligne.put(KEY_COMMENTS, seance.getComments());
        ligne.put(KEY_CLIENTID, seance.getClientId());
        ligne.put(KEY_MONITOR, seance.getMonitorId());
        ligne.put(KEY_DURATION, seance.getDurationMinut());
        ligne.put(KEY_START, seance.getStartDate());
        getWritableDatabase().insertWithOnConflict(TABLE_SEANCES, null, ligne, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //    public void saveNote(Note note)
//    {
//
//        ContentValues ligne =new ContentValues();
//        ligne.put("id",note.getId());
//        ligne.put("notes",note.getNotes());
//
//        ligne.put("date",note.getDate());
//
//        getWritableDatabase().insertWithOnConflict("NOTES",null,ligne,SQLiteDatabase.CONFLICT_REPLACE);
//    }
    public List<Note> readNote() {

        List<Note> NoteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("NOTES", new String[]{"id", "date", "notes"}, null,
                null, null, null, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            //cursor is empty
            Log.w("readnote", " not found !!!");
            return null;
        } else {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    NoteList.add(new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)));
                    cursor.moveToNext();
                }
            }
            return NoteList;

        }
    }


    void saveNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("notes", String.valueOf(note.getNotes()));
            values.put("date", String.valueOf(note.getDate()));
            db.insert("NOTES", null, values);
            db.close();
        } catch (Exception ex) {
            db.close();
            ex.printStackTrace();
        }
    }

    void updateNote(int id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE NOTES SET notes ='" + note + "' WHERE id = " + id);
        db.close();
    }

    void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM NOTES WHERE id = " + id);
        db.close();
    }


    Note readNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NOTES WHERE id=?", new String[]{String.valueOf(id)});
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            //cursor is empty
            Log.d("readnoteid", "not found !!!");
            db.close();
            return null;
        } else {
            Note r = new Note();
            r.setId(cursor.getInt(0));
            r.setNotes(cursor.getString(1));
            r.setDate(cursor.getString(2));
            db.close();
            return r;
        }
    }

    public void saveTask(Task task) {

        ContentValues ligne = new ContentValues();
        ligne.put(KEY_TID, task.getTaskId());
        ligne.put(KEY_TSTART, task.getStartDate());
        ligne.put(KEY_TDURATION, task.getDurationMinut());
        ligne.put(KEY_TTITLE, task.getTitle());
        ligne.put(KEY_TDETAIL, task.getDetail());
        ligne.put(KEY_TISDone, task.getIsDone());
        ligne.put(KEY_TUSERFK, task.getUserFk());
        getWritableDatabase().insertWithOnConflict(TABLE_TASKS, null, ligne, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Task readTask(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_TID,
                        KEY_TSTART, KEY_TDURATION, KEY_TTITLE, KEY_TDETAIL, KEY_TISDone, KEY_TUSERFK}, KEY_SID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)));
        // return contact
        return task;
    }

    public List<Seance> readSeance() {

        SQLiteDatabase db = this.getReadableDatabase();


        List<Seance> seanceList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SEANCES, new String[]{KEY_SID,
                        KEY_COMMENTS, KEY_CLIENTID, KEY_MONITOR, KEY_DURATION, KEY_START}, null,
                null, null, null, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            //cursor is empty
            Log.d("readseance", " not found !!!");
            return null;
        } else {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    seanceList.add(new Seance(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                            Integer.parseInt(cursor.getString(4)), cursor.getString(5)));
                    cursor.moveToNext();
                }
            }
            return seanceList;

        }
    }

    public List<Seance> readUserSeance(int id) {

        SQLiteDatabase db = this.getReadableDatabase();


        List<Seance> seanceList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SEANCES, new String[]{KEY_SID,
                        KEY_COMMENTS, KEY_CLIENTID, KEY_MONITOR, KEY_DURATION, KEY_START}, KEY_MONITOR + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            //cursor is empty
            Log.d("readuserseance", "not found !!!");
            return null;
        } else {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    seanceList.add(new Seance(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                            Integer.parseInt(cursor.getString(4)), cursor.getString(5)));
                    cursor.moveToNext();
                }
            }
            return seanceList;

        }
    }

    public List<Seance> readClientSeance(int id) {

        SQLiteDatabase db = this.getReadableDatabase();


        List<Seance> seanceList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SEANCES, new String[]{KEY_SID,
                        KEY_COMMENTS, KEY_CLIENTID, KEY_MONITOR, KEY_DURATION, KEY_START}, KEY_CLIENTID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            //cursor is empty
            Log.d("readclientseance", "not found !!!");
            return null;
        } else {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    seanceList.add(new Seance(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                            Integer.parseInt(cursor.getString(4)), cursor.getString(5)));
                    cursor.moveToNext();
                }
            }
            return seanceList;

        }
    }

    void addClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_ID, client.getClientId());
        values.put(KEY_NAME, client.getfName());
        values.put(KEY_LAST_NAME, client.getlName());
        values.put(KEY_PHOTO, client.getPhoto());
        values.put(KEY_IDENTITY, client.getIdentityDoc());
        values.put(KEY_MAIL, client.getClientEmail());
        values.put(KEY_PASSWORD, client.getPasswd());
        values.put(KEY_NOTES, client.getNotes());
        values.put(KEY_PH_NO, client.getClientPhone());

        db.insert(TABLE_CLIENTS, null, values);

        db.close();
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_UID, user.getUserId());
        values.put(KEY_UFNAME, user.getUserFname());
        values.put(KEY_ULNAME, user.getUserLname());
        values.put(KEY_UPHOTO, user.getUserphoto());
        values.put(KEY_UEMAIL, user.getUserEmail());
        values.put(KEY_UPASSWD, user.getUserPasswd());
        values.put(KEY_UDESCRIPTION, user.getDescription());
        values.put(KEY_UTYPE, user.getUserType());
        values.put(KEY_UPHONE, user.getUserPhone());


        db.insert(TABLE_USERS, null, values);

        db.close();
    }


    Client getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLIENTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_LAST_NAME, KEY_PHOTO, KEY_IDENTITY, KEY_MAIL, KEY_PASSWORD, KEY_PH_NO, KEY_NOTES}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Client client = new Client(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8));
        // return contact
        return client;
    }


    public List<Client> getAllContacts() {
        List<Client> contactList = new ArrayList<Client>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


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

                contactList.add(contact);
            } while (cursor.moveToNext());
        }


        return contactList;
    }


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


        return db.update(TABLE_CLIENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(client.getClientId())});
    }


    public void deleteContact(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(client.getClientId())});
        db.close();
    }


    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();


        return cursor.getCount();
    }

}