package schedule.schedule.reader

import schedule.schedule.model.Schedule
import java.time.LocalDateTime

interface ScheduleReader {

    fun findScheduleByIdOrNull(scheduleId: Long): Schedule?

    fun isScheduleAvailable(roomId: Long, startTime: LocalDateTime, endTime: LocalDateTime): Boolean
    fun isParticipantAlreadyReserved(participantIds: List<Long>, startTime: LocalDateTime, endTime: LocalDateTime): Boolean
}