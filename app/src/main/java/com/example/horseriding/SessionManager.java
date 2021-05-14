package com.example.horseriding;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;
    private  static final  String IS_LOGIN="IsLoggedIn";
    public static final String KEY_FULLNAME="fullName";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="passwd";
    public static final String KEY_PHONENUMBER="phone";
    public static final String KEY_ID="id";
    public static final String KEY_TYPE="type";
    public static final String KEY_DESCRIPTION="description";
    public static final String KEY_PHOTO="photo";

    public SessionManager(Context context) {
        this.context = context;
        usersSession=this.context.getSharedPreferences("userSession",Context.MODE_PRIVATE);
        editor=usersSession.edit();

    }
    public void createLoginSession(User user)
    {
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_FULLNAME, user.getUserFname()+" "+user.getUserLname());
        editor.putString(KEY_EMAIL, user.getUserEmail());
        editor.putString(KEY_PASSWORD, user.getUserPasswd());
        editor.putString(KEY_PHONENUMBER, user.getUserPhone());
        editor.putString(KEY_ID, String.valueOf(user.getUserId()));
        editor.putString(KEY_TYPE, user.getUserType());
        editor.putString(KEY_PHOTO, user.getUserphoto());
        editor.putString(KEY_DESCRIPTION, user.getDescription());
        editor.commit();

    }
    public HashMap<String,String> getUserDetailFromSession()
    {
        HashMap<String,String> userData =new HashMap<String, String>();
        userData.put(KEY_FULLNAME,usersSession.getString(KEY_FULLNAME,null));
        userData.put(KEY_EMAIL,usersSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PASSWORD,usersSession.getString(KEY_PASSWORD,null));
        userData.put(KEY_PHONENUMBER,usersSession.getString(KEY_PHONENUMBER,null));
        userData.put(KEY_ID,usersSession.getString(KEY_ID,null));
        userData.put(KEY_TYPE,usersSession.getString(KEY_TYPE,null));
        userData.put(KEY_PHOTO,usersSession.getString(KEY_PHOTO,null));
        userData.put(KEY_DESCRIPTION,usersSession.getString(KEY_DESCRIPTION,null));
        return userData;
    }
    public boolean chekingLogin()
    {
        return usersSession.getBoolean(IS_LOGIN,false)?true:false;
        /*if(usersSession.getBoolean(IS_LOGIN,true))
        {
            return true;
        }
        return false;*/
    }
    public void logout()
    {
        editor.clear();
        editor.commit();
    }
}
