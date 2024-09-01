package schedule.input.controller.schedule.request

import com.fasterxml.jackson.annotation.JsonProperty
import schedule.extension.toLocalDateTime
import schedule.model.CreateScheduleCommand
import java.time.LocalDateTime

data class CreateScheduleRequest(
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

    fun toCommand(): CreateScheduleCommand = CreateScheduleCommand(
        name = name,
        startTime = startTime.toLocalDateTime(),
        endTime = endTime.toLocalDateTime(),
        roomId = roomId,
        participants = participants
    )
}
