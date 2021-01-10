package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Period {
    private static Date startDate;
    private static Date endDate;
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    static {
        try {
            startDate = simpleDateFormat.parse("11/01/2021 00:00:00");
            endDate = simpleDateFormat.parse("11/01/2021 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public Period() {
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static boolean isInPeriod() {
        Date currentDate = new Date(System.currentTimeMillis());
        return currentDate.after(startDate) && currentDate.before(endDate);
    }
}
