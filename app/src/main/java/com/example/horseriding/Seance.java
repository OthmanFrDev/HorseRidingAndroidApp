package com.example.horseriding;

public class Seance {
    private int seanceId,seanceGrpId,clientId,monitorId,durationMinut;
    private String comments;

    public Seance(int seanceId, int seanceGrpId, int clientId, int monitorId, int durationMinut, String comments) {
        this.seanceId = seanceId;
        this.seanceGrpId = seanceGrpId;
        this.clientId = clientId;
        this.monitorId = monitorId;
        this.durationMinut = durationMinut;
        this.comments = comments;
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
