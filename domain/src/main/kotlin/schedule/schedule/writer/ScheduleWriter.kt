package schedule.schedule.writer

import schedule.schedule.model.Schedule

interface ScheduleWriter {

    fun save(schedule: Schedule): Schedule

    fun delete(schedule: Schedule)
}