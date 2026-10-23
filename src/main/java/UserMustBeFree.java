class UserMustBeFree implements BookingRule {
    private final IBookingRepository bookings;

    UserMustBeFree(IBookingRepository bookings) { this.bookings = bookings; }

    public void check(String userId, String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException("user already has a booking at that time");
            }
        }
    }
}