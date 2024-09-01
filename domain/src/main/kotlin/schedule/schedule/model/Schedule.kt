package schedule.schedule.model

import java.time.LocalDateTime

data class Schedule(
    val id: Long = 0L,
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomId: Long,
    // roomId와의 위상을 맞추기 위해 이름을 participantIds로 변경
    val participantIds: List<Long>
)
