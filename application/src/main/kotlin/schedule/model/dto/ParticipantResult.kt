package schedule.model.dto

import schedule.user.model.User

data class ParticipantResult(
    val id: Long,
    val name: String,
) {

    companion object {
        fun of(user: User) = ParticipantResult(
            id = user.id,
            name = user.name,
        )
    }
}
