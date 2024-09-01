package schedule.model

import schedule.schedule.model.Schedule
import java.time.LocalDateTime

data class CreateScheduleCommand(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>,
) {

    fun toSchedule() = Schedule(
        name = name,
        startTime = startTime,
        endTime = endTime,
        roomId = roomId,
        participants = participants,
    )
}
