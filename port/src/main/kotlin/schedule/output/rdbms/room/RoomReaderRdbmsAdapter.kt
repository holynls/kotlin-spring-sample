package schedule.output.rdbms.room

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import schedule.exception.ResourceNotFoundException
import schedule.output.rdbms.room.repository.RoomRepository
import schedule.room.model.Room
import schedule.room.reader.RoomReader

@Component
class RoomReaderRdbmsAdapter(
    private val roomRepository: RoomRepository,
) : RoomReader {

    override fun findRoomByIdOrNull(id: Long): Room? {
        return roomRepository.findByIdOrNull(id)?.toDomain()
    }

}