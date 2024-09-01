package schedule.room.reader

import schedule.room.model.Room

interface RoomReader {

    fun findRoomByIdOrNull(id: Long): Room?
}