import java.util.*;

class MemoryBookingRepository implements IBookingRepository {
    private final List<Booking> bookings = new ArrayList<>();
    @Override public void save(Booking booking) { bookings.add(booking); }
    @Override public void delete(Booking booking) { bookings.remove(booking); }
    @Override public List<Booking> findByRoom(String roomId) {
        return bookings.stream().filter(b -> b.getRoomId().equals(roomId)).toList();
    }
    @Override public List<Booking> findByUser(String userId) {
        return bookings.stream().filter(b -> b.getUserId().equals(userId)).toList();
    }
}