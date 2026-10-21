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
}
