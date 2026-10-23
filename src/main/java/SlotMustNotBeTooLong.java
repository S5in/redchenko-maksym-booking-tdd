class SlotMustNotBeTooLong implements BookingRule {
    public void check(String userId, String roomId, TimeSlot slot) {
        if (slot.duration().compareTo(BookingService.MAX_SLOT) > 0) {
            throw new IllegalArgumentException(
                    "slot must not be longer than " + BookingService.MAX_SLOT);
        }
    }
}