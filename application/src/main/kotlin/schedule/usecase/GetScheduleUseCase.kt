package schedule.usecase

import schedule.model.GetScheduleResult

interface GetScheduleUseCase {

    fun getSchedule(id: Long): GetScheduleResult
}