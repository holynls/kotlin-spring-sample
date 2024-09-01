package schedule.output.rdbms.schedule.entity

import jakarta.persistence.*

@Entity
@Table(name = "schedule_participants")
class ScheduleParticipantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: Long = 0,

    val scheduleId: Long = 0,
) {

    companion object {
        fun of(userId: Long, scheduleId: Long) = ScheduleParticipantEntity(
            userId = userId,
            scheduleId = scheduleId
        )
    }
}