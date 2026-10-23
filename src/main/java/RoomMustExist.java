class RoomMustExist implements BookingRule {
    private final IRoomRepository rooms;

    RoomMustExist(IRoomRepository rooms) { this.rooms = rooms; }

    public void check(String userId, String roomId, TimeSlot slot) {
        if (rooms.findById(roomId) == null) {
            throw new IllegalArgumentException("unknown room: " + roomId);
        }
    }
}