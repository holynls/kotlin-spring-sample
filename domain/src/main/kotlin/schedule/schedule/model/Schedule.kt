package schedule.schedule.model

import java.time.LocalDateTime

data class Schedule(
    val id: Long = 0L,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    val participants: List<Long>
)
