package schedule.model

import java.time.LocalDateTime

data class UpdateScheduleCommand(
    val id: Long,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>,
)
