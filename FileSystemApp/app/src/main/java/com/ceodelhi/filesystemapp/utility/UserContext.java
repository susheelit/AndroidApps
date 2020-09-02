package com.ceodelhi.filesystemapp.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by win7 on 1/12/2017.
 */

public class UserContext {
    public static String mobilenumber;
    public static String email;
    public static String SMS_SenderId = "CEODEL";
    public static final String OTP_DELIMITER = ":";
    public static String Ac_no = "";
    public static String Part_No = "";
    public static String Srl_No = "";
    public static String fatherName = "";
    public static String Voters_Name = "";
    public static String Voters_Address = "";
    public static String pollingAddress = "";
    public static String Section_No = "";
    public static String IDCARD_NO = "";
    public static String SEX = "";
    public static String Age = "";
    public static String RLN_TYPE = "";
    public static String RLN_NAME = "";
    public static String HOUSE_No = "";
    public static String Flage_InsertiedBy = "M";


    public static String dateFormat1(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,  hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(dateString));
        return formatter.format(calendar.getTime());
    }

    //14-8-2020
    public static String dateFormat_date(String dateString) {
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(fromUser.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;

    }

}
