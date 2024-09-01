package schedule.model

import schedule.model.dto.ParticipantResult
import schedule.model.dto.RoomResult
import schedule.room.model.Room
import schedule.schedule.model.Schedule
import schedule.user.model.User
import java.time.LocalDateTime

data class GetScheduleResult(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val room: RoomResult,
    val participants: List<ParticipantResult>
) {

    companion object {
        fun from(
            schedule: Schedule,
            room: Room,
            participants: List<User>
        ) = GetScheduleResult(
            id = schedule.id,
            name = schedule.name,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            room = RoomResult.of(room),
            participants = participants.map { ParticipantResult.of(it) }
        )
    }
}
