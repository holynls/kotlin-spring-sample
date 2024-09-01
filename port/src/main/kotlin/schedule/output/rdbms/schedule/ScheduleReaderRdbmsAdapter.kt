package schedule.output.rdbms.schedule

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import schedule.output.rdbms.schedule.entity.QScheduleEntity
import schedule.output.rdbms.schedule.entity.QScheduleParticipantEntity
import schedule.output.rdbms.schedule.entity.ScheduleEntity
import schedule.output.rdbms.schedule.repository.ParticipantRepository
import schedule.output.rdbms.schedule.repository.ScheduleRepository
import schedule.schedule.model.Schedule
import schedule.schedule.reader.ScheduleReader
import java.time.LocalDateTime

@Component
class ScheduleReaderRdbmsAdapter(
    private val scheduleRepository: ScheduleRepository,
    private val participantRepository: ParticipantRepository,
): ScheduleReader,
    QuerydslRepositorySupport(ScheduleEntity::class.java) {

    private val schedule = QScheduleEntity.scheduleEntity
    override fun findScheduleByIdOrNull(scheduleId: Long): Schedule? {
        val schedule = scheduleRepository.findByIdOrNull(scheduleId)
            ?: return null

        val participantIds = participantRepository.findAllByScheduleId(scheduleId)
            .map { it.userId }

        return Schedule(
            id = schedule.id,
            name = schedule.name,
            roomId = schedule.roomId,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            participants = participantIds
        )
    }

    override fun isScheduleAvailable(
        roomId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        scheduleId: Long?
    ): Boolean {
        val query = from(schedule)
            .where(schedule.roomId.eq(roomId)
                .and(schedule.startTime.lt(endTime))
                .and(schedule.endTime.gt(startTime)))

        if (scheduleId != null) {
            query.where(schedule.id.ne(scheduleId))
        }

        val count = query.fetchCount()

        return count == 0L;
    }

    override fun isParticipantAlreadyReserved(
        participantIds: List<Long>,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        scheduleId: Long?,
    ): Boolean {
        val participant = QScheduleParticipantEntity.scheduleParticipantEntity

        val query = from(schedule)
            .join(participant).on(schedule.id.eq(participant.scheduleId))
            .where(participant.userId.`in`(participantIds)
                .and(schedule.startTime.lt(endTime))
                .and(schedule.endTime.gt(startTime)))

        if (scheduleId != null) {
            query.where(schedule.id.ne(scheduleId))
        }

        val count = query.fetchCount()

        return count > 0L;
    }
}