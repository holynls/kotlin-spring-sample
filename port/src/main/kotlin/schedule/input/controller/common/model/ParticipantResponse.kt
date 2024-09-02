package schedule.input.controller.common.model

import io.swagger.v3.oas.annotations.media.Schema
import schedule.model.dto.ParticipantResult

@Schema(description = "회의 참여자 상세 정보")
data class ParticipantResponse(
    @Schema(description = "참여자 ID", example = "1")
    val id: Long,
    @Schema(description = "참여자 이름", example = "홍길동")
    val name: String,
) {

    companion object {
        fun of(participantResult: ParticipantResult) = ParticipantResponse(
            id = participantResult.id,
            name = participantResult.name,
        )
    }
}
