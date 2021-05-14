package com.example.horseriding;

public class Task {
    private int taskId,durationMinut,userFk;
    private String title,detail;

    public Task(int taskId, int durationMinut, int userFk, String title, String detail) {
        this.taskId = taskId;
        this.durationMinut = durationMinut;
        this.userFk = userFk;
        this.title = title;
        this.detail = detail;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getDurationMinut() {
        return durationMinut;
    }

    public void setDurationMinut(int durationMinut) {
        this.durationMinut = durationMinut;
    }

    public int getUserFk() {
        return userFk;
    }

    public void setUserFk(int userFk) {
        this.userFk = userFk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}