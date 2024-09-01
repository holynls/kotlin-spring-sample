package schedule.input.controller.schedule.response

import com.fasterxml.jackson.annotation.JsonProperty
import schedule.extension.toTimeFormat
import schedule.model.CreateScheduleResult
import java.time.LocalDateTime

data class CreateScheduleResponse(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("start_time")
    val startTime: String,

    @JsonProperty("end_time")
    val endTime: String,

    @JsonProperty("room_id")
    val roomId: Long,

    @JsonProperty("participants")
    val participants: List<Long>
) {
    companion object {
        fun of(result: CreateScheduleResult) = CreateScheduleResponse(
            id = result.id,
            name = result.name,
            startTime = result.startTime.toTimeFormat(),
            endTime = result.endTime.toTimeFormat(),
            roomId = result.roomId,
            participants = result.participants
        )
    }
}
