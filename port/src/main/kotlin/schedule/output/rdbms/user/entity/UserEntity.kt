package schedule.output.rdbms.user.entity

import jakarta.persistence.*
import schedule.user.model.User

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,
) {

    fun toDomain() = User(
        id = id,
        name = name,
    )

    companion object {
        fun from(user: User) = UserEntity(
            id = user.id,
            name = user.name,
        )
    }
}