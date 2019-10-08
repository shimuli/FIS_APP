package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ReportsClass {

    private String diaryid,report_date,report,teacher_commment,parent_comment;

    public ReportsClass(String diaryid, String report_date, String report, String teacher_commment, String parent_comment) {
        this.diaryid = diaryid;
        this.report_date = report_date;
        this.report = report;
        this.teacher_commment = teacher_commment;
        this.parent_comment = parent_comment;
    }

    public String getReport_date() {
        DateTimeFormatter inputFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(report_date, inputFormatter);
            String NewDate = outputFormatter.format(date);
            return NewDate;
        }
        return report_date;
    }

    public String getDiaryID() {
        return diaryid;
    }
    public String getReport() {
        return report;
    }

    public String getTeacher_commment() {
        return teacher_commment;
    }

    public String getParent_comment() {
        return parent_comment;
    }
}
