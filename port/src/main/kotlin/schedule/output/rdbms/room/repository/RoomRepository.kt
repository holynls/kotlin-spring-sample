package schedule.output.rdbms.room.repository

import org.springframework.data.jpa.repository.JpaRepository
import schedule.output.rdbms.room.entity.RoomEntity

interface RoomRepository : JpaRepository<RoomEntity, Long> {
}