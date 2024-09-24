import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        DateParser dateParser = new DateParser();
        dateParser.printDate(LocalDateTime.now());

    }
}