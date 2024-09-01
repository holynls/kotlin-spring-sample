package schedule.input.controller.common.model

import schedule.model.dto.ParticipantResult

data class ParticipantResponse(
    val id: Long,
    val name: String,
) {

    companion object {
        fun of(participantResult: ParticipantResult) = ParticipantResponse(
            id = participantResult.id,
            name = participantResult.name,
        )
    }
}
