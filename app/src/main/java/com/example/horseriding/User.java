package com.example.horseriding;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class User {
private int userId;
private String userEmail,userPasswd,userFname,userLname,description,userType,userphoto,userPhone;
    private List<Seance> seances;

    public User(int userId, String userEmail, String userPasswd, String userFname, String userLname, String description, String userType, String userphoto, String userPhone) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPasswd = userPasswd;
        this.userFname = userFname;
        this.userLname = userLname;
        this.description = description;
        this.userType = userType;
        this.userphoto = userphoto;
        this.userPhone = userPhone;
    }
    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }
    public   void addSeance(Seance seance)
    {
        if(seances==null)
        {seances=new ArrayList<Seance>();}
        seances.add(seance);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
