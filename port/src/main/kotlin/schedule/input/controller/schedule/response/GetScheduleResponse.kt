package schedule.input.controller.schedule.response

import schedule.input.controller.common.model.ParticipantResponse
import schedule.input.controller.common.model.RoomResponse
import schedule.model.GetScheduleResult
import java.time.LocalDateTime

data class GetScheduleResponse(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val room: RoomResponse,
    val participants: List<ParticipantResponse>
) {

    companion object {
        fun of(getScheduleResult: GetScheduleResult) = GetScheduleResponse(
            id = getScheduleResult.id,
            name = getScheduleResult.name,
            startTime = getScheduleResult.startTime,
            endTime = getScheduleResult.endTime,
            room = RoomResponse.of(getScheduleResult.room),
            participants = getScheduleResult.participants.map { ParticipantResponse.of(it) }
        )
    }
}
