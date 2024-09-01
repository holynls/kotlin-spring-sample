package schedule.usecase

import schedule.model.CreateScheduleCommand
import schedule.model.CreateScheduleResult

interface CreateScheduleUseCase {

    fun create(command: CreateScheduleCommand): CreateScheduleResult
}