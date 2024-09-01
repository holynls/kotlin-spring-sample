package schedule.output.rdbms.schedule.repository

import org.springframework.data.jpa.repository.JpaRepository
import schedule.output.rdbms.schedule.entity.ScheduleEntity

interface ScheduleRepository: JpaRepository<ScheduleEntity, Long> {


}