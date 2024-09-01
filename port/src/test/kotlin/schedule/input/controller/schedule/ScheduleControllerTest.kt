package schedule.input.controller.schedule

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import schedule.input.controller.schedule.request.CreateScheduleRequest
import schedule.input.controller.schedule.request.UpdateScheduleRequest

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


    @Nested
    inner class GetSchedule {
        @Test
        fun `스케쥴 조회 성공`() {
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

            mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isOk)
                // 현재 실행시마다 유저와 룸에 랜덤한 데이터가 들어가므로 리턴값 비교는 하지 않습니다.
                //.andExpect(content().json("""{id:1}"")
        }
    }


    @Nested
    inner class UpdateSchedule {
        @Test
        fun `스케쥴 업데이트 성공`() {
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

            val updateRequest = UpdateScheduleRequest(
                name = "회의",
                roomId = 1,
                startTime = "2024-12-01T10:00:00",
                endTime = "2024-12-01T11:00:00",
                participants = listOf(1, 2, 3)
            )

            mockMvc.perform(put("/schedules/1"))
                .andExpect(status().isOk)
            // 현재 실행시마다 유저와 룸에 랜덤한 데이터가 들어가므로 리턴값 비교는 하지 않습니다.
            //.andExpect(content().json("""{id:1}"")
        }
    }
}