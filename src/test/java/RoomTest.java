import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    @Test
    void roomKeepsItsId() {
        Room room = new Room("R1", "Room 1");
        assertEquals("R1", room.getId());
    }
}