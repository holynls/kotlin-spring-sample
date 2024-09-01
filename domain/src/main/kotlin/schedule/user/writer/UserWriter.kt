package schedule.user.writer

import schedule.user.model.User

interface UserWriter {
    fun createUsers(users: List<User>): List<User>
}