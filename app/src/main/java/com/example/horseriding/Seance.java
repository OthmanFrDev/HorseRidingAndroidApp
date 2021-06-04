package com.example.horseriding;

import java.io.Serializable;

public class Seance implements Serializable {
    private int seanceId, seanceGrpId, clientId, monitorId, durationMinut;
    private String comments, startDate;

    public Seance(int seanceId, String comments, int clientId, int monitorId, int durationMinut, String startDate) {
        this.seanceId = seanceId;
        this.clientId = clientId;
        this.monitorId = monitorId;
        this.durationMinut = durationMinut;
        this.comments = comments;
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Seance(int seanceId, int seanceGrpId, int clientId, int monitorId, int durationMinut, String comments, String startDate) {
        this.seanceId = seanceId;
        this.seanceGrpId = seanceGrpId;
        this.clientId = clientId;
        this.monitorId = monitorId;
        this.durationMinut = durationMinut;
        this.comments = comments;
        this.startDate = startDate;
    }

    public int getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(int seanceId) {
        this.seanceId = seanceId;
    }

    public int getSeanceGrpId() {
        return seanceGrpId;
    }

    public void setSeanceGrpId(int seanceGrpId) {
        this.seanceGrpId = seanceGrpId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public int getDurationMinut() {
        return durationMinut;
    }

    public void setDurationMinut(int durationMinut) {
        this.durationMinut = durationMinut;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
