package com.example.horseriding;

public class Client {
    private int clientId;
    private String fName,lName,photo,identityDoc,clientEmail,passwd,clientPhone,notes;

    public Client(int clientId, String fName, String lName, String photo, String identityDoc, String clientEmail, String passwd, String clientPhone, String notes) {
        this.clientId = clientId;
        this.fName = fName;
        this.lName = lName;
        this.photo = photo;
        this.identityDoc = identityDoc;
        this.clientEmail = clientEmail;
        this.passwd = passwd;
        this.clientPhone = clientPhone;
        this.notes = notes;
    }

    public Client() {

    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIdentityDoc() {
        return identityDoc;
    }

    public void setIdentityDoc(String identityDoc) {
        this.identityDoc = identityDoc;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", photo='" + photo + '\'' +
                ", identityDoc='" + identityDoc + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", passwd='" + passwd + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
