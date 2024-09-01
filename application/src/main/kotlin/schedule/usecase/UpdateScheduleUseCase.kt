package schedule.usecase

import schedule.model.UpdateScheduleCommand
import schedule.model.UpdateScheduleResult

interface UpdateScheduleUseCase {

    fun update(command: UpdateScheduleCommand): UpdateScheduleResult
}