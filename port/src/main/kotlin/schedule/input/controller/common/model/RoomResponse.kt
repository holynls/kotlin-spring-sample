package schedule.input.controller.common.model

import schedule.model.dto.RoomResult

data class RoomResponse(
    val id: Long,
    val name: String,
    val capacity: Int,
) {

    companion object {
        fun of(roomResult: RoomResult): RoomResponse {
            return RoomResponse(
                id = roomResult.id,
                name = roomResult.name,
                capacity = roomResult.capacity,
            )
        }
    }
}
