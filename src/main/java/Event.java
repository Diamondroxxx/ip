import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Event extends Task{
    private final LocalDateTime startsAt;
    private final LocalDateTime endsAt;

    public Event(String task, LocalDateTime from, LocalDateTime to) {
        super(task);
        startsAt = from;
        endsAt = to;
    }

    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (" + formatDate(startsAt)
                + " – " + formatDate(endsAt) + ")";
    }
}
