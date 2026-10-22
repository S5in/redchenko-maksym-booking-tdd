import java.util.*;

interface IRoomRepository {
    Room findById(String id); // null if no such room
    void save(Room room);
    List<Room> findAll();
}