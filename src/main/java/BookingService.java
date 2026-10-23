import java.time.*;
import java.util.Comparator;
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
        new RoomMustExist(rooms).check(userId, roomId, slot);
        new SlotMustBeInFuture(clock).check(userId, roomId, slot);
        new SlotMustNotBeTooLong().check(userId, roomId, slot);
        new RoomMustBeFree(bookings).check(userId, roomId, slot);
        new UserMustBeFree(bookings).check(userId, roomId, slot);
        Booking booking = new Booking(userId, roomId, slot);
        bookings.save(booking);
        return booking;
    }

    /** Cancel a user's booking. */
    public void cancel(String userId, String roomId, TimeSlot slot) {
        Booking booking = new Booking(userId, roomId, slot);
        if (!bookings.findByUser(userId).contains(booking)) {
            throw new IllegalArgumentException("no such booking");
        }
        bookings.delete(booking);
    }

    /** A user's bookings, earliest first. */
    public List<Booking> bookingsFor(String userId) {
        return bookings.findByUser(userId).stream()
                .sorted(Comparator.comparing(b -> b.getSlot().getStart()))
                .toList();
    }
}