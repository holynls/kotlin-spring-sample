package schedule.output.rdbms.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import schedule.output.rdbms.user.repository.UserRepository
import schedule.user.model.User
import schedule.user.reader.UserReader

@Component
class UserReaderRdbmsAdapter(
    private val userRepository: UserRepository,
) : UserReader {
    override fun findUserByIdOrNull(id: Long): User? =
        userRepository.findByIdOrNull(id)?.toDomain()

    override fun findUsers(ids: List<Long>): List<User> =
        userRepository.findAllById(ids).map { it.toDomain() }
}