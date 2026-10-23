import java.time.*;

class SlotMustBeInFuture implements BookingRule {
    private final Clock clock;

    SlotMustBeInFuture(Clock clock) { this.clock = clock; }

    public void check(String userId, String roomId, TimeSlot slot) {
        if (slot.getStart().isBefore(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("slot must be in the future");
        }
    }
}