import java.time.LocalDateTime;

public class DateParser {

    public void printDate(LocalDateTime date) {
//        год:месяц:день##:час:минут:секунды:милисекунды
        int year = date.getYear();
        int month = date.getMonthValue();
        String monthProcessed = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
        int day = date.getDayOfMonth();
        String dayProcessed = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
        int hour = date.getHour();
        int minute = date.getMinute();
        int second = date.getSecond();
        int millisecond = date.getNano() / 1000;

        System.out.printf("%d:%s:%s##:%d:%d:%d:%d", year, monthProcessed, dayProcessed, hour, minute, second, millisecond);
    }
}
