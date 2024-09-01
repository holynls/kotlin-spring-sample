package schedule.input.controller.schedule.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import schedule.extension.toTimeFormat
import schedule.model.CreateScheduleResult
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateScheduleResponse(
    val id: Long,
    val name: String,
    @JsonProperty("start_time")
    val startTime: String,
    @JsonProperty("end_time")
    val endTime: String,
    @JsonProperty("room_id")
    val roomId: Long,
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
