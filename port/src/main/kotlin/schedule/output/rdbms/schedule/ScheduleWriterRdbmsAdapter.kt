package schedule.output.rdbms.schedule

import org.springframework.stereotype.Component
import schedule.output.rdbms.schedule.entity.ScheduleEntity
import schedule.output.rdbms.schedule.entity.ScheduleParticipantEntity
import schedule.output.rdbms.schedule.repository.ParticipantRepository
import schedule.output.rdbms.schedule.repository.ScheduleRepository
import schedule.schedule.model.Schedule
import schedule.schedule.writer.ScheduleWriter

@Component
class ScheduleWriterRdbmsAdapter(
    private val scheduleRepository: ScheduleRepository,
    private val scheduleParticipantRepository: ParticipantRepository,
) : ScheduleWriter {

    override fun save(schedule: Schedule): Schedule {
        val scheduleToBeSave = ScheduleEntity.of(schedule)

        val scheduleEntity = scheduleRepository.save(scheduleToBeSave)

        val participantsToBeSave = schedule.participantIds.map {
            ScheduleParticipantEntity.of(it, scheduleEntity.id)
        }

        val participantEntities = scheduleParticipantRepository.saveAll(participantsToBeSave)

        return Schedule(
            id = scheduleEntity.id,
            name = scheduleEntity.name,
            startTime = scheduleEntity.startTime,
            endTime = scheduleEntity.endTime,
            roomId = scheduleEntity.roomId,
            participantIds = participantEntities.map { it.userId }
        )
    }
}