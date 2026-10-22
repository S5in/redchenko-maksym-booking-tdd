import java.util.Objects;

public final class Booking {
    private final String userId;
    private final String roomId;
    private final TimeSlot slot;

    public Booking(String userId, String roomId, TimeSlot slot) {
        this.userId = userId;
        this.roomId = roomId;
        this.slot = slot;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public TimeSlot getSlot() {
        return slot;
    }
        @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) o;
        return userId.equals(other.userId)
                && roomId.equals(other.roomId)
                && slot.equals(other.slot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roomId, slot);
    }
}