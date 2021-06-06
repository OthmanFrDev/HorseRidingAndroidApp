package com.example.horseriding;

import java.io.Serializable;

public class Task implements Serializable {
    private int taskId, durationMinut, userFk;
    private String title, detail, startDate, isDone;

    public Task(int taskId, String startDate, int durationMinut, String title, String detail, String isDone, int userFk) {
        this.taskId = taskId;
        this.durationMinut = durationMinut;
        this.userFk = userFk;
        this.title = title;
        this.detail = detail;
        this.startDate = startDate;
        this.isDone = isDone;
    }

    public Task(int taskId, int durationMinut, int userFk, String title, String detail) {
        this.taskId = taskId;
        this.durationMinut = durationMinut;
        this.userFk = userFk;
        this.title = title;
        this.detail = detail;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    public Task() {

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
