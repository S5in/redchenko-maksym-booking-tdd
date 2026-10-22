import java.time.*;
import java.util.List;

public class BookingService {
    public static final Duration MAX_SLOT = Duration.ofHours(3);

    private final IRoomRepository rooms;
    private final IBookingRepository bookings;
    private final Clock clock;

    public BookingService(IRoomRepository rooms, IBookingRepository bookings, Clock clock) {
        this.rooms = rooms;
        this.bookings = bookings;
        this.clock = clock;
    }

    /** Book a room for a user. Returns the new booking. */
    public Booking book(String userId, String roomId, TimeSlot slot) {
        if (rooms.findById(roomId) == null) {
            throw new IllegalArgumentException("unknown room: " + roomId);
        }
        if (slot.getStart().isBefore(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("slot must be in the future");
        }
        if (slot.duration().compareTo(MAX_SLOT) > 0) {
            throw new IllegalArgumentException("slot must not be longer than " + MAX_SLOT);
        }
        for (Booking existing : bookings.findByRoom(roomId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException("room already booked for that time");
            }
        }
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException("user already has a booking at that time");
            }
        }
        Booking booking = new Booking(userId, roomId, slot);
        bookings.save(booking);
        return booking;
    }
    /** Cancel a user's booking. */
    public void cancel(String userId, String roomId, TimeSlot slot) {
        throw new UnsupportedOperationException("TODO: implement using TDD");
    }

    /** A user's bookings, earliest first. */
    public List<Booking> bookingsFor(String userId) {
        throw new UnsupportedOperationException("TODO: implement using TDD");
    }
}
