package schedule.model

import java.time.LocalDateTime

data class CreateScheduleCommand(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>,
)
