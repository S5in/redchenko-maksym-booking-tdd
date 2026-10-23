interface BookingRule {
    void check(String userId, String roomId, TimeSlot slot);
}