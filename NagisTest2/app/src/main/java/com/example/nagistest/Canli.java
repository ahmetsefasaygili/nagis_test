package com.example.nagistest;

public class Canli {


    String canliid;
    String canliad;
    String canlisoyad;
    String mesaj;

    public Canli(){

    }

    public Canli(String canliid, String canliad, String canlisoyad, String mesaj) {
        this.canliid = canliid;
        this.canliad = canliad;
        this.canlisoyad = canlisoyad;
        this.mesaj = mesaj;
    }

    public String getCanliid() {
        return canliid;
    }

    public void setCanliid(String canliid) {
        this.canliid = canliid;
    }

    public String getCanliad() {
        return canliad;
    }

    public void setCanliad(String canliad) {
        this.canliad = canliad;
    }

    public String getCanlisoyad() {
        return canlisoyad;
    }

    public void setCanlisoyad(String canlisoyad) {
        this.canlisoyad = canlisoyad;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }


}
