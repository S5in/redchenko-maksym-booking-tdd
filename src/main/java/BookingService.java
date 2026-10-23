import java.time.*;
import java.util.Comparator;
import java.util.List;

public class BookingService {
    public static final Duration MAX_SLOT = Duration.ofHours(3);
    private final IBookingRepository bookings;
    private final List<BookingRule> rules;

     public BookingService(IRoomRepository rooms, IBookingRepository bookings, Clock clock) {
        this.bookings = bookings;
        this.rules = List.of(
                new RoomMustExist(rooms),
                new SlotMustBeInFuture(clock),
                new SlotMustNotBeTooLong(),
                new RoomMustBeFree(bookings),
                new UserMustBeFree(bookings));
    }

    /** Book a room for a user. Returns the new booking. */
    public Booking book(String userId, String roomId, TimeSlot slot) {
        for (BookingRule rule : rules) {
            rule.check(userId, roomId, slot);
        }
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