package schedule.output.rdbms.room

import org.springframework.stereotype.Component
import schedule.output.rdbms.room.entity.RoomEntity
import schedule.output.rdbms.room.repository.RoomRepository
import schedule.room.model.Room
import schedule.room.writer.RoomWriter

@Component
class RoomWriterRdbmsAdapter(
    private val roomRepository: RoomRepository
) : RoomWriter {

    override fun createRoom(room: Room): Room {
        return roomRepository.save(RoomEntity.from(room)).toDomain()
    }

    override fun createRooms(rooms: List<Room>): List<Room> {
        return roomRepository.saveAll(rooms.map { RoomEntity.from(it) }).map { it.toDomain() }
    }
}