import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    public void printInFormat(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd'##':HH:mm:ss:SSS");
        System.out.println(date.format(formatter));
    }

}
