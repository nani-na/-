package com.dckj.runnablea.ui.home.bean;

import org.litepal.crud.LitePalSupport;

public class CityBean extends LitePalSupport {

    /**
     * class_id : 3408
     * class_parent_id : 3401
     * class_name : 肥西县
     * class_type : 3
     */

    private int class_id;
    private int class_parent_id;
    private String class_name;
    private String initial;     //首字母
    private String lowercase;     //小写全拼
    private String uppercase;     //大写全拼
    private int class_type;

    public String getUppercase() {
        return uppercase;
    }

    public void setUppercase(String uppercase) {
        this.uppercase = uppercase;
    }

    public String getLowercase() {
        return lowercase;
    }

    public void setLowercase(String lowercase) {
        this.lowercase = lowercase;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getClass_parent_id() {
        return class_parent_id;
    }

    public void setClass_parent_id(int class_parent_id) {
        this.class_parent_id = class_parent_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_type() {
        return class_type;
    }

    public void setClass_type(int class_type) {
        this.class_type = class_type;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "class_id=" + class_id +
                ", class_parent_id=" + class_parent_id +
                ", class_name='" + class_name + '\'' +
                ", initial='" + initial + '\'' +
                ", lowercase='" + lowercase + '\'' +
                ", uppercase='" + uppercase + '\'' +
                ", class_type=" + class_type +
                '}';
    }
}
