package com.rfid.mascir.hello;

/**
 * Created by mac1 on 4/19/16.
 */
public class Doctor {
    private int idDoc;
    private String adress;
    private String Dlan;
    private String Dlong;
    private int Vaccin_idVaccin;


    public Doctor() {
    }

    public Doctor(int idDoc, int vaccin_idVaccin) {
        this.idDoc = idDoc;
        Vaccin_idVaccin = vaccin_idVaccin;
    }

    public Doctor(int idDoc) {
        this.idDoc = idDoc;

    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setDlan(String dlan) {
        Dlan = dlan;
    }

    public void setDlong(String dlong) {
        Dlong = dlong;
    }

    public void setVaccin_idVaccin(int vaccin_idVaccin) {
        Vaccin_idVaccin = vaccin_idVaccin;
    }

    public int getIdDoc() {
        return idDoc;
    }

    public String getAdress() {
        return adress;
    }

    public String getDlan() {
        return Dlan;
    }

    public String getDlong() {
        return Dlong;
    }

    public int getVaccin_idVaccin() {
        return Vaccin_idVaccin;
    }
}
