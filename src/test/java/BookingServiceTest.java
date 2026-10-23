import org.junit.jupiter.api.*;
import java.time.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {
    // "Now" is always 09:00 on 11 January 2027 in these tests.
    private static final Clock CLOCK =
        Clock.fixed(Instant.parse("2027-01-11T09:00:00Z"), ZoneOffset.UTC);

    private BookingService service;

    @BeforeEach
    void setUp() {
        MemoryRoomRepository rooms = new MemoryRoomRepository();
        rooms.save(new Room("R1", "Room 1"));
        rooms.save(new Room("R2", "Room 2"));
        service = new BookingService(rooms, new MemoryBookingRepository(), CLOCK);
    }

    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(LocalDateTime.of(2027, 1, 11, startHour, 0),
                            LocalDateTime.of(2027, 1, 11, endHour, 0));
    }

    @Test
    void bookingAFreeRoomReturnsTheBooking() {
        Booking booking = service.book("alice", "R1", slot(10, 11));
        assertEquals(new Booking("alice", "R1", slot(10, 11)), booking);
    }
    @Test
    void bookingAnUnknownRoomIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> service.book("alice", "R9", slot(10, 11)));
    }
    @Test
    void bookingASlotInThePastIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> service.book("alice", "R1", slot(8, 9)));
    }
    @Test
    void bookingALongerThanThreeHoursSlotIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> service.book("alice", "R1", slot(10, 14)));
    }
    @Test
    void bookingAnOverlappingSlotInTheSameRoomIsRejected() {
        service.book("alice", "R1", slot(10, 12));
        assertThrows(IllegalStateException.class,
                () -> service.book("bob", "R1", slot(11, 13)));
    }
    @Test
    void userCannotHoldOverlappingBookingsInDifferentRooms() {
        service.book("alice", "R1", slot(10, 12));
        assertThrows(IllegalStateException.class,
                () -> service.book("alice", "R2", slot(11, 13)));
    }
    @Test
    void cancellingABookingFreesTheSlot() {
        service.book("alice", "R1", slot(10, 11));
        service.cancel("alice", "R1", slot(10, 11));
        Booking booking = service.book("bob", "R1", slot(10, 11));
        assertEquals(new Booking("bob", "R1", slot(10, 11)), booking);
    }
    @Test
    void cancellingABookingThatDoesNotExistIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cancel("alice", "R1", slot(10, 11)));
    }
    @Test
    void bookingsForReturnsTheUsersBookings() {
        service.book("alice", "R1", slot(10, 11));
        assertEquals(List.of(new Booking("alice", "R1", slot(10, 11))),
                service.bookingsFor("alice"));
    }
    @Test
    void bookingsForReturnsTheBookingsEarliestFirst() {
        service.book("alice", "R1", slot(14, 15));
        service.book("alice", "R1", slot(10, 11));
        assertEquals(List.of(new Booking("alice", "R1", slot(10, 11)),
                            new Booking("alice", "R1", slot(14, 15))),
                service.bookingsFor("alice"));
    }
    @Test
    void aSlotOfExactlyThreeHoursIsAllowed() {
        Booking booking = service.book("alice", "R1", slot(10, 13));
        assertEquals(new Booking("alice", "R1", slot(10, 13)), booking);
    }
    @Test
    void touchingSlotsInTheSameRoomAreAllowed() {
        service.book("alice", "R1", slot(10, 11));
        Booking booking = service.book("bob", "R1", slot(11, 12));
        assertEquals(new Booking("bob", "R1", slot(11, 12)), booking);
    }
    @Test
    void sameUserSameSlotInTwoRoomsIsRejected() {
        service.book("alice", "R1", slot(10, 11));
        assertThrows(IllegalStateException.class,
                () -> service.book("alice", "R2", slot(10, 11)));
    }
    @Test
    void aFourthActiveBookingIsRejected() {
        service.book("alice", "R1", slot(10, 11));
        service.book("alice", "R1", slot(11, 12));
        service.book("alice", "R1", slot(12, 13));
        assertThrows(IllegalStateException.class,
                () -> service.book("alice", "R1", slot(13, 14)));
    }
    @Test
    void aSlotMoreThanFourteenDaysAheadIsRejected() {
        TimeSlot farAway = new TimeSlot(LocalDateTime.of(2027, 1, 26, 10, 0),
                                        LocalDateTime.of(2027, 1, 26, 11, 0));
        assertThrows(IllegalArgumentException.class,
                () -> service.book("alice", "R1", farAway));
    }
    @Test
    void aSlotExactlyFourteenDaysAheadIsAllowed() {
        TimeSlot lastDay = new TimeSlot(LocalDateTime.of(2027, 1, 25, 10, 0),
                                        LocalDateTime.of(2027, 1, 25, 11, 0));
        assertEquals(new Booking("alice", "R1", lastDay), service.book("alice", "R1", lastDay));
    }
}
