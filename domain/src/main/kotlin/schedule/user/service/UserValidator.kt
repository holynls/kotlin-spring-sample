package schedule.user.service

import org.springframework.stereotype.Service
import schedule.exception.InvalidRequestException
import schedule.exception.ResourceNotFoundException
import schedule.user.reader.UserReader

@Service
class UserValidator(
    private val userReader: UserReader,
) {

    fun validateUser(userIds: List<Long>) {
        val users = userReader.findUsers(userIds)

        if (users.size != userIds.size) {
            throw ResourceNotFoundException("존재하지 않는 유저가 포함되어 있습니다.")
        }
    }
}