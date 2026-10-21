import java.time.LocalDateTime;

public final class TimeSlot {
    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end must be after start");
        }
    }
}