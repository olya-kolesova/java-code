import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeParser dateTimeParser = new DateTimeParser();
        dateTimeParser.printInFormat(dateTime);
    }
}