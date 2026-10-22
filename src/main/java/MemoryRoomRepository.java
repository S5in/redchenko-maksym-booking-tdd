import java.util.*;

class MemoryRoomRepository implements IRoomRepository {
    private final Map<String, Room> rooms = new HashMap<>();
    @Override public Room findById(String id) { return rooms.get(id); }
    @Override public void save(Room room) { rooms.put(room.getId(), room); }
    @Override public List<Room> findAll() { return new ArrayList<>(rooms.values()); }
}