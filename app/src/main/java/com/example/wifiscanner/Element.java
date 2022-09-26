package com.example.wifiscanner;

import java.io.Serializable;

public class Element implements Serializable {
    private String name;
    private String BSSID;
    private String secLevel;
    private int signLevel;
    private int freq;


    public Element(String name, String BSSID, String secLevel, int signLevel, int freq) {
        this.name = name;
        this.BSSID = BSSID;
        this.secLevel = secLevel;
        this.signLevel = signLevel;
        this.freq = freq;
    }

    public String getName() {
        return name;
    }

    public String getBSSID() {
        return BSSID;
    }

    public String getSecLevel() {
        return secLevel;
    }

    public int getSignLevel() {
        return signLevel;
    }

    public int getFreq() {
        return freq;
    }
}