package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    private DateUtils(){}
    public static Date convertStringDateToDate(String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-yyyy HH:MM:SS");
        try {

            return formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
