package schedule.input.controller.schedule.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import schedule.extension.toTimeFormat
import schedule.model.UpdateScheduleResult

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(description = "일정 수정 응답")
data class UpdateScheduleResponse(
    @Schema(description = "일정 ID")
    val id: Long,
    @Schema(description = "일정 이름")
    val name: String,
    @JsonProperty("start_time")
    @Schema(description = "일정 시작 시간", format = "yyyy-MM-ddTHH:mm:ss", example = "2021-01-01T00:00:00")
    val startTime: String,
    @JsonProperty("end_time")
    @Schema(description = "알종 종료 시간", format = "yyyy-MM-ddTHH:mm:ss", example = "2021-01-01T00:00:00")
    val endTime: String,
    @JsonProperty("room_id")
    @Schema(description = "회의실 ID")
    val roomId: Long,
    @Schema(description = "참여자 ID 목록")
    val participants: List<Long>
) {

    companion object {
        fun of(result: UpdateScheduleResult) = UpdateScheduleResponse(
            id = result.id,
            name = result.name,
            startTime = result.startTime.toTimeFormat(),
            endTime = result.endTime.toTimeFormat(),
            roomId = result.roomId,
            participants = result.participants
        )
    }
}
