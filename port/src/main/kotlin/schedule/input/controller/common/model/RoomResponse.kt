package schedule.input.controller.common.model

import io.swagger.v3.oas.annotations.media.Schema
import schedule.model.dto.RoomResult

data class RoomResponse(
    @Schema(description = "회의실 ID", example = "1")
    val id: Long,
    @Schema(description = "회의실 이름", example = "회의실1")
    val name: String,
    @Schema(description = "수용인원", example = "10")
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
