package schedule.output.rdbms.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import schedule.output.rdbms.user.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long> {
}