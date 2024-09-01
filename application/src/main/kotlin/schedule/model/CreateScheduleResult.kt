package schedule.model

import schedule.schedule.model.Schedule
import java.time.LocalDateTime

data class CreateScheduleResult(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>,
) {

    companion object {
        fun from(schedule: Schedule) = CreateScheduleResult(
            id = schedule.id,
            name = schedule.name,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            roomId = schedule.roomId,
            participants = schedule.participants
        )
    }
}
