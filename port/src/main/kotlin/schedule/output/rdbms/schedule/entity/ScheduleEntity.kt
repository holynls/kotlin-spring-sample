package schedule.output.rdbms.schedule.entity

import jakarta.persistence.*
import schedule.schedule.model.Schedule
import java.time.LocalDateTime

@Entity
@Table(name = "schedules")
class ScheduleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "name")
    var name: String,

    @Column(name = "start_time")
    var startTime: LocalDateTime,

    @Column(name = "end_time")
    var endTime: LocalDateTime,

    @Column(name = "room_id")
    var roomId: Long,
) {

    fun toDomain(participants: List<ScheduleParticipantEntity>) = Schedule(
        id = id,
        name = name,
        startTime = startTime,
        endTime = endTime,
        roomId = roomId,
        participantIds = participants.map { it.userId }
    )

    companion object {
        fun of(schedule: Schedule) = ScheduleEntity(
            id = schedule.id,
            name = schedule.name,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            roomId = schedule.roomId,
        )
    }
}