package schedule.model

import schedule.schedule.model.Schedule
import java.time.LocalDateTime

data class UpdateScheduleCommand(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>,
) {

    fun toSchedule() = Schedule(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime,
        roomId = roomId,
        participants = participants,
    )
}
