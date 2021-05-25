package com.example.horseriding;


import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
private int userId,adminLevel,isActive;
private String userEmail,userPasswd,userFname,userLname,description,userType,userphoto,userPhone;

    private List<Seance> seances;
    private  List<Task> tasks;

    @Override
    public String toString() {
        return userFname+" " +userLname;
    }

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
    public void setSeances(Seance seance) {
        seances.add(seance);
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

    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
