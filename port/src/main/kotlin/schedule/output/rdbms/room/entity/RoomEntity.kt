package schedule.output.rdbms.room.entity

import jakarta.persistence.*
import schedule.room.model.Room

@Entity
@Table(name = "rooms")
class RoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var capacity: Int,
) {

    fun toDomain() = Room(id, name, capacity)

    companion object {
        fun from(room: Room) = RoomEntity(room.id, room.name, room.capacity)
    }
}