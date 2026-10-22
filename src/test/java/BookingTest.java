import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BookingTest {
    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(LocalDateTime.of(2027, 1, 11, startHour, 0),
                LocalDateTime.of(2027, 1, 11, endHour, 0));
    }

    @Test
    void bookingKeepsItsUserRoomAndSlot() {
        TimeSlot slot = slot(10, 11);
        Booking booking = new Booking("alice", "R1", slot);
        assertEquals("alice", booking.getUserId());
        assertEquals("R1", booking.getRoomId());
        assertEquals(slot, booking.getSlot());
    }
}