package com.dckj.runnablea.bean;

public class CharBean {
    private String charname;
    private int po;

    public CharBean(String charname, int po) {
        this.charname = charname;
        this.po = po;
    }

    public int getPo() {
        return po;
    }

    public void setPo(int po) {
        this.po = po;
    }

    public String getCharname() {
        return charname;
    }

    public void setCharname(String charname) {
        this.charname = charname;
    }

    @Override
    public String toString() {
        return "CharBean{" +
                "charname='" + charname + '\'' +
                ", po=" + po +
                '}';
    }

}
