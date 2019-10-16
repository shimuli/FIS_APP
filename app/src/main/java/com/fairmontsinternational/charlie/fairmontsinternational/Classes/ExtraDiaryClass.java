package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ExtraDiaryClass {
    public String getDiarytitle() {
        return diarytitle;
    }

    public String getDiarydate() {

        return diarydate;
    }

    public String getstudentId(){
        return studentId;
    }


    public String getteacherComment(){
        return teacherComment;
    }

    public String getparentComment(){
        return parentComment;
    }

    public String diarytitle, studentId,  teacherComment, parentComment;

    public ExtraDiaryClass(String diarytitle, String diarydate, String studentId,String teacherComment, String parentComment) {
        this.diarytitle = diarytitle;
        this.diarydate = diarydate;
        this.studentId = studentId;
        this.teacherComment = teacherComment;
        this.parentComment = parentComment;
    }

    public String diarydate;
}
