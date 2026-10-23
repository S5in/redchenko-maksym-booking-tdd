import java.time.*;

class SlotMustStartWithinFourteenDays implements BookingRule {
    private static final int MAX_DAYS_AHEAD = 14;
    private final Clock clock;

    SlotMustStartWithinFourteenDays(Clock clock) { this.clock = clock; }

    public void check(String userId, String roomId, TimeSlot slot) {
        LocalDate lastAllowedDay = LocalDate.now(clock).plusDays(MAX_DAYS_AHEAD);
        if (slot.getStart().toLocalDate().isAfter(lastAllowedDay)) {
            throw new IllegalArgumentException("slot is more than 14 days ahead");
        }
    }
}