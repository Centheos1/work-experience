package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class Encrypt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String pvt;
    private String pub;
    private String checkKey;

    public Encrypt() {
    }

    public Encrypt(String pvt, String pub, String checkKey) {
        this.pvt = pvt;
        this.pub = pub;
        this.checkKey = checkKey;
    }

    public long getId() {
        return id;
    }

    public String getPvt() {
        return pvt;
    }

    public void setPvt(String pvt) {
        this.pvt = pvt;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getCheckKey() {
        return checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }
}
