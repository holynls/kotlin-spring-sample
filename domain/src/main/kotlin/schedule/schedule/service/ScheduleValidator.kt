package schedule.schedule.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import schedule.exception.AlreadyReservedException
import schedule.schedule.reader.ScheduleReader
import java.time.LocalDateTime

@Service
class ScheduleValidator(
    private val scheduleReader: ScheduleReader,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun validateCreateSchedule(
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        roomId: Long,
        participantIds: List<Long>,
        scheduleId: Long? = null,
    ) {
        when {
            !scheduleReader.isScheduleAvailable(roomId, startTime, endTime, scheduleId) -> {
                log.error("Room is already reserved. roomId: $roomId, startTime: $startTime, endTime: $endTime")
                throw AlreadyReservedException("선택한 회의실은 해당 시간에 이미 예약되었습니다.")
            }

            scheduleReader.isParticipantAlreadyReserved(participantIds, startTime, endTime, scheduleId) -> {
                log.error("Participant is already reserved. participant: $participantIds, startTime: $startTime, endTime: $endTime")
                throw AlreadyReservedException("참여자 중 일부는 해당 시간에 이미 예약되어 있습니다.")
            }
        }
    }
}