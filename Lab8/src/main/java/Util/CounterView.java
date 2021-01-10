package Util;


import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Named
@ViewScoped
public class CounterView implements Serializable {

    private static int number = 0;

    public int getNumber() {
        return number;
    }

    public String calculateLeftTime() {

        Date currentDate = null;

        currentDate = new Date(System.currentTimeMillis());
        if (Period.getEndDate().before(currentDate)) {
            return "00:00";
        }

        long diff = Period.getEndDate().getTime() - currentDate.getTime();
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        int diffHours = (int) (diffMinutes/60);
        long minutes = diffMinutes % 60;
        String result = diffHours + ":" + minutes;
        return result;
    }
}
