package schedule.input.controller.schedule

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import schedule.input.controller.schedule.request.CreateScheduleRequest

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest(
    @Autowired
    private val mockMvc: MockMvc
) {

    private val objectMapper = jacksonObjectMapper()

    @Nested
    inner class CreateSchedule {

        @Test
        fun `스케쥴 생성 성공`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 1,
                startTime = "2024-12-01T10:00:00",
                endTime = "2024-12-01T11:00:00",
                participants = listOf(1, 2, 3)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk)
        }
    }
}