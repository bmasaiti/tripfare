package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date convertStringDateToDate(String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YYYY HH:MM:SS");
        try {

            Date date = formatter.parse(strDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }
}
