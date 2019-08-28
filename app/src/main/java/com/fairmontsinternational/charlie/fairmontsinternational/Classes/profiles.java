package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

public class profiles {

    private String childname, admissionNo,level;

    public profiles(String childname, String admissionNo, String level) {
        this.childname = childname;
        this.admissionNo = admissionNo;
        this.level = level;
    }

    public String getChildname() {
        return childname;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public String getLevel() {
        return level;
    }
}
