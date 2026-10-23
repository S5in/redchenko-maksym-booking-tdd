class RoomMustBeFree implements BookingRule {
    private final IBookingRepository bookings;

    RoomMustBeFree(IBookingRepository bookings) { this.bookings = bookings; }

    public void check(String userId, String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByRoom(roomId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException("room already booked for that time");
            }
        }
    }
}