package schedule.output.rdbms.user

import org.springframework.stereotype.Component
import schedule.output.rdbms.user.entity.UserEntity
import schedule.output.rdbms.user.repository.UserRepository
import schedule.user.model.User
import schedule.user.writer.UserWriter

@Component
class UserWriterRdbmsAdapter(
    private val userRepository: UserRepository,
) : UserWriter {
    override fun createUsers(users: List<User>): List<User> {
        return userRepository.saveAll(users.map { UserEntity.from(it) }).map { it.toDomain() }
    }
}