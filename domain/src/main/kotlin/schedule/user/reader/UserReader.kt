package schedule.user.reader

import schedule.user.model.User

interface UserReader {
    fun findUserByIdOrNull(id: Long): User?
    fun findUsers(ids: List<Long>): List<User>
}