import java.time.*;

class UserBookingLimit implements BookingRule {
    private static final int MAX_ACTIVE = 3;
    private final IBookingRepository bookings;
    private final Clock clock;

    UserBookingLimit(IBookingRepository bookings, Clock clock) {
        this.bookings = bookings;
        this.clock = clock;
    }

    public void check(String userId, String roomId, TimeSlot slot) {
        LocalDateTime now = LocalDateTime.now(clock);
        long active = bookings.findByUser(userId).stream()
                .filter(b -> b.getSlot().getEnd().isAfter(now))
                .count();
        if (active >= MAX_ACTIVE) {
            throw new IllegalStateException("user already has 3 active bookings");
        }
    }
}