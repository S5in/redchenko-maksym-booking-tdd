import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {
    @Test
    void endBeforeStartIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        LocalDateTime nine = LocalDateTime.of(2027, 1, 11, 9, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(ten, nine));
    }
    @Test
    void endEqualToStartIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(ten, ten));
    }
    @Test
    void nullStartIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(null, ten));
    }
    @Test
    void nullEndIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(ten, null));
    }
    @Test
    void getStartAndGetEndReturnTheGivenTimes() {
        LocalDateTime nine = LocalDateTime.of(2027, 1, 11, 9, 0);
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        TimeSlot slot = new TimeSlot(nine, ten);
        assertEquals(nine, slot.getStart());
        assertEquals(ten, slot.getEnd());
    }
}
