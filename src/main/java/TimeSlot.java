import java.time.LocalDateTime;

public final class TimeSlot {
    public TimeSlot(LocalDateTime start, LocalDateTime end) {
    if (start == null) {
        throw new IllegalArgumentException("start must not be null");
    }
    if (!end.isAfter(start)) {
        throw new IllegalArgumentException("end must be after start");
    }
}
}