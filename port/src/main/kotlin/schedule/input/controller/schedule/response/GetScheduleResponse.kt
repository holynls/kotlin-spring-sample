package schedule.input.controller.schedule.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import schedule.extension.toTimeFormat
import schedule.input.controller.common.model.ParticipantResponse
import schedule.input.controller.common.model.RoomResponse
import schedule.model.GetScheduleResult

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(description = "일정 상세 조회 응답")
data class GetScheduleResponse(
    @Schema(description = "일정 ID", example = "1")
    val id: Long,
    @Schema(description = "일정 이름", example = "회의")
    val name: String,
    @JsonProperty("start_time")
    @Schema(description = "시작 시간", format = "yyyy-MM-ddTHH:mm:ss", example = "2021-01-01T00:00:00")
    val startTime: String,
    @JsonProperty("end_time")
    @Schema(description = "종료 시간", format = "yyyy-MM-ddTHH:mm:ss", example = "2021-01-01T00:00:00")
    val endTime: String,
    @Schema(description = "회의실 정보")
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
