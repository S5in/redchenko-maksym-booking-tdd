import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @Test
    void durationIsTheTimeFromStartToEnd() {
        TimeSlot slot = new TimeSlot(
                LocalDateTime.of(2027, 1, 11, 10, 0),
                LocalDateTime.of(2027, 1, 11, 11, 30));
        assertEquals(Duration.ofMinutes(90), slot.duration());
    }
    private static TimeSlot slot(int startHour, int endHour) {
    return new TimeSlot(LocalDateTime.of(2027, 1, 11, startHour, 0),
            LocalDateTime.of(2027, 1, 11, endHour, 0));
}

    @ParameterizedTest(name = "{0}-{1} vs {2}-{3} overlaps: {4}")
    @CsvSource({
        "10, 11, 11, 12, false", // touching
        "10, 12, 11, 13, true",  // partly overlapping
        "9, 10, 11, 12, false",  // a before b
        "11, 12, 10, 13, true",  // a inside b
        "10, 11, 10, 11, true",  // identical
        "13, 14, 10, 11, false"  // a after b
    })
    void overlapCases(int aStart, int aEnd, int bStart, int bEnd, boolean expected) {
        assertEquals(expected, slot(aStart, aEnd).overlaps(slot(bStart, bEnd)));
        assertEquals(expected, slot(bStart, bEnd).overlaps(slot(aStart, aEnd))); // A4
    }
    @Test
    void slotsWithSameStartAndEndAreEqualWithSameHashCode() {
        TimeSlot a = slot(10, 11);
        TimeSlot b = slot(10, 11);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
    @Test
    void slotsWithDifferentTimesAreNotEqual() {
        assertNotEquals(slot(10, 11), slot(10, 12)); // end differs
        assertNotEquals(slot(10, 12), slot(11, 12)); // start differs
    }
    @Test
    void toStringShowsStartAndEnd() {
        String text = slot(10, 11).toString();
        assertTrue(text.contains("2027-01-11T10:00"));
        assertTrue(text.contains("2027-01-11T11:00"));
    }
}
