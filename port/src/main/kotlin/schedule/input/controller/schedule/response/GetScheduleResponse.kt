package schedule.input.controller.schedule.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import schedule.extension.toTimeFormat
import schedule.input.controller.common.model.ParticipantResponse
import schedule.input.controller.common.model.RoomResponse
import schedule.model.GetScheduleResult

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetScheduleResponse(
    val id: Long,
    val name: String,
    @JsonProperty("start_time")
    val startTime: String,
    @JsonProperty("end_time")
    val endTime: String,
    val room: RoomResponse,
    val participants: List<ParticipantResponse>
) {

    companion object {
        fun of(getScheduleResult: GetScheduleResult) = GetScheduleResponse(
            id = getScheduleResult.id,
            name = getScheduleResult.name,
            startTime = getScheduleResult.startTime.toTimeFormat(),
            endTime = getScheduleResult.endTime.toTimeFormat(),
            room = RoomResponse.of(getScheduleResult.room),
            participants = getScheduleResult.participants.map { ParticipantResponse.of(it) }
        )
    }
}
