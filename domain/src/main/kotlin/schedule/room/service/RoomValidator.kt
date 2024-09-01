package schedule.room.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import schedule.exception.InvalidRequestException
import schedule.exception.ResourceNotFoundException
import schedule.room.reader.RoomReader

@Service
class RoomValidator(
    private val roomReader: RoomReader
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun validateReserveRoom(roomId: Long, participantCount: Int) {
        val room = roomReader.findRoomByIdOrNull(roomId) ?:
            throw ResourceNotFoundException("요청한 일정 또는 회의실을 찾을 수 없습니다.")

        if (room.capacity < participantCount) {
            log.error("Room with id $roomId has not enough capacity")
            throw InvalidRequestException("회의실의 최대 수용 가능 인원을 초과했습니다.")
        }
    }
}