package schedule.output.rdbms.schedule.repository

import org.springframework.data.jpa.repository.JpaRepository
import schedule.output.rdbms.schedule.entity.ScheduleParticipantEntity

interface ParticipantRepository: JpaRepository<ScheduleParticipantEntity, Long> {

    fun findAllByScheduleId(scheduleId: Long): List<ScheduleParticipantEntity>
    fun deleteAllByScheduleId(scheduleId: Long)
}