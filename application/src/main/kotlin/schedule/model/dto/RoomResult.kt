package schedule.model.dto

import schedule.room.model.Room

data class RoomResult(
    val id: Long,
    val name: String,
    val capacity: Int,
) {

    companion object {
        fun of(room: Room) = RoomResult(
            id = room.id,
            name = room.name,
            capacity = room.capacity,
        )
    }
}
