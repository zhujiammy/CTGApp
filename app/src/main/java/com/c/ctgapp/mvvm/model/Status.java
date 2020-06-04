package com.c.ctgapp.mvvm.model;

public class Status {
    private int level;
    private int type;
    private int myinfo;
    private Certificationinfo certificationinfo;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Certificationinfo getCertificationinfo() {
        return certificationinfo;
    }

    public void setCertificationinfo(Certificationinfo certificationinfo) {
        this.certificationinfo = certificationinfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMyinfo() {
        return myinfo;
    }

    public void setMyinfo(int myinfo) {
        this.myinfo = myinfo;
    }

}
