package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

import com.android.volley.toolbox.StringRequest;

import io.paperdb.Paper;

public class BaseUrl {

    public  static  String ss = Paper.book().read("Main_url").toString();

    public static String returnBase()
    {
        String base = Paper.book().read("Main_url").toString();
        return  base;
    }
    public static String fetchProfiles(String phone){
        String url= ss+"api/fetchprofiles/?id="+phone;
        return url;
    }

    public static String firstlogin(String phone)
    {
        String url = ss+"api/firstlogin?phone_no="+phone;
        return url;
    }

    public static String addpassword(String phone,String password)
    {
        String url = ss+"api/addpassword?phone_no="+phone+"&password="+password;
        return url;
    }
    public static String addcomment(String diaryid,String date,String comment)
    {
        String url = ss+"api/addcomment?diary_id="+diaryid+"&date="+date+"&comment="+comment;
        return url;
    }
    public static String updatePassword(String phone,String newpass,String oldpass)
    {
        String url = ss+"api/updatepassword?phone_no="+phone+"&new_password="+newpass+"&old_password="+oldpass;
        return url;
    }

    public static String forgotPassword(String phone,String newpass)
    {
        String url = ss+"api/forgotpassword?phone_no="+phone+"&new_password="+newpass;
        return url;
    }

    public static String attendance(String admNo,String date)
    {
        String url = ss+"api/fetchattendance?admission_no="+admNo+"&date="+date;
        return url;
    }

    public static String fetchsingleprofile(String admission_no)
    {
        String url = ss+"api/fetchsingleprofile/?id="+admission_no;
        return url;
    }

    public static String login(String phone_no,String password)
    {
        String url = ss+"api/login?phone_no="+phone_no+"&password="+password;
        return  url;
    }

    public static String fetchfeesrecords(String admission_no)
    {
        String url = ss+"api/fetchfeesrecords?id="+admission_no;
        return url;
    }
    public static String fetchname(String phone)
    {
        String url = ss+"api/fetchname/?id="+phone;
        return url;
    }

    public static String fetchfees(String admission_no){
        String url = ss+"api/fetchfees/?id="+admission_no;
        return url;
    }
    public static String fetchfeeinvoice(String admission_no){
        String url = ss+"api/FetchCurrentTermInvoice/?id="+admission_no;
        return url;
    }
    public static String fetchfeesinvoicetotals(String admission_no){
        String url = ss+"api/fetchTotalCurrentTermFeeInvoice/?id="+admission_no;
        return url;
    }

    public static String searchreports(String admission_no,String start_date,String end_date){
        String url = ss + "api/searchreports?admission_no=" +admission_no+"&start_date="+start_date+"&end_date="+end_date;
        return  url;
    }

    public static String fetchreports(String admission_no){
        String url = ss+"api/fetchreports/?id="+admission_no;
        return url;
    }

}
