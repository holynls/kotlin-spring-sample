package schedule.room.writer

import schedule.room.model.Room

interface RoomWriter {

    fun createRoom(room: Room): Room
    fun createRooms(rooms: List<Room>): List<Room>
}