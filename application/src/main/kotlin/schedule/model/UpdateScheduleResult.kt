package schedule.model

import schedule.schedule.model.Schedule
import java.time.LocalDateTime

data class UpdateScheduleResult(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>
) {

    companion object {
        fun from(schedule: Schedule): UpdateScheduleResult {
            return UpdateScheduleResult(
                id = schedule.id,
                name = schedule.name,
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                roomId = schedule.roomId,
                participants = schedule.participantIds
            )
        }
    }
}
