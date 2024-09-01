package schedule.application

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import schedule.exception.InvalidRequestException
import schedule.exception.ResourceNotFoundException
import schedule.model.*
import schedule.room.reader.RoomReader
import schedule.room.service.RoomValidator
import schedule.schedule.model.Schedule
import schedule.schedule.reader.ScheduleReader
import schedule.schedule.service.ScheduleValidator
import schedule.schedule.writer.ScheduleWriter
import schedule.usecase.CreateScheduleUseCase
import schedule.usecase.GetScheduleUseCase
import schedule.usecase.UpdateScheduleUseCase
import schedule.user.reader.UserReader
import schedule.user.service.UserValidator
import java.time.LocalDateTime

@Service
class ScheduleApplicationService(
    private val scheduleValidator: ScheduleValidator,
    private val roomValidator: RoomValidator,
    private val userValidator: UserValidator,
    private val scheduleWriter: ScheduleWriter,
    private val scheduleReader: ScheduleReader,
    private val roomReader: RoomReader,
    private val userReader: UserReader,
) : CreateScheduleUseCase, GetScheduleUseCase, UpdateScheduleUseCase {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun create(command: CreateScheduleCommand): CreateScheduleResult {
        val (name, startTime, endTime, roomId, participants) = command

        validateSchedule(startTime, endTime, roomId, participants)

        val scheduleToBeSave = command.toSchedule()

        val schedule = scheduleWriter.save(scheduleToBeSave)

        return CreateScheduleResult.from(schedule)
    }

    @Transactional(readOnly = true)
    override fun getSchedule(id: Long): GetScheduleResult {
        val schedule = scheduleReader.findScheduleByIdOrNull(id)
            ?: throw ResourceNotFoundException("해당 ID의 예약이 존재하지 않습니다. id: $id")

        val room = roomReader.findRoomByIdOrNull(schedule.roomId)
            ?: throw ResourceNotFoundException("해당 ID의 회의실이 존재하지 않습니다. roomId: ${schedule.roomId}")

        val participants = userReader.findUsers(schedule.participants)

        if (participants.size != schedule.participants.size) {
            val notFoundUserIds = schedule.participants - participants.map { it.id }.toSet()
            log.error("참가자 정보를 찾을 수 없음. notFoundUserIds: $notFoundUserIds")
            throw ResourceNotFoundException("참가자 정보를 찾을 수 없습니다. notFoundUserIds: $notFoundUserIds")
        }

        return GetScheduleResult.from(schedule, room, participants)
    }

    @Transactional
    override fun update(command: UpdateScheduleCommand): UpdateScheduleResult {
        val (id, name, startTime, endTime, roomId, participants) = command

        val schedule = scheduleReader.findScheduleByIdOrNull(id)
            ?: throw ResourceNotFoundException("해당 ID의 예약이 존재하지 않습니다. id: $id")

        // transacional을 사용하면 update를 위한 delete와 insert가 같은 트랜잭션으로 묶이기 때문에
        scheduleWriter.delete(schedule)

        validateSchedule(startTime, endTime, roomId, participants)

        val scheduleToBeUpdate = command.toSchedule()

        val updatedSchedule = scheduleWriter.save(scheduleToBeUpdate)

        return UpdateScheduleResult.from(updatedSchedule)
    }

    private fun validateSchedule(
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        roomId: Long,
        participants: List<Long>,
    ) {

        when {
            startTime.isAfter(endTime) -> {
                log.error("잘못된 예약 요청. 시작 시간이 종료 시간보다 이후. startTime: $startTime, endTime: $endTime")
                throw InvalidRequestException("시작 시간은 종료 시간보다 이전이어야 합니다. startTime: $startTime, endTime: $endTime")
            }

            startTime.isBefore(LocalDateTime.now()) -> {
                log.error("잘못된 예약 요청. 시작 시간이 현재시간보다 빠름. startTime: $startTime, currentTime: ${LocalDateTime.now()}")
                throw InvalidRequestException("시작 시간은 현재 시간 이후여야 합니다. startTime: $startTime")
            }

            participants.isEmpty() -> {
                log.error("잘못된 예약 요청. 참가자는 최소 1명 이상이어야 합니다. participants: $participants")
                throw InvalidRequestException("참가자는 최소 1명 이상이어야 합니다. participants: $participants")
            }
        }

        scheduleValidator.validateCreateSchedule(startTime, endTime, roomId, participants)
        roomValidator.validateReserveRoom(roomId, participants.size)
        userValidator.validateUser(participants)
    }
}