package schedule.input.controller.schedule

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValues
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import schedule.input.controller.schedule.request.CreateScheduleRequest
import schedule.input.controller.schedule.request.UpdateScheduleRequest
import schedule.input.controller.schedule.response.CreateScheduleResponse

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
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(1, 2, 3)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk)
        }

        // 따로 실행하면 위의 테스트에서 생성된 회의가 없어 실패할 수 있음
        @Test
        fun `스케쥴 생성 실패 - 다른 회의와 겹침`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 1,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(4, 5, 6)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict)
        }

        // 따로 실행하면 위의 테스트에서 생성된 회의가 없어 실패할 수 있음
        @Test
        fun `스케쥴 생성 실패 - 참가자가 이미 다른 시간대 회의 참가중`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 2,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(1, 4, 5)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict)
        }

        @Test
        fun `스케쥴 생성 실패 - 회의실 최대 인원 초과`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 2,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(4, 5, 6, 7, 8, 9)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest)
        }

        @Test
        fun `스케쥴 생성 실패 - 인원을 선택하지 않음`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 2,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf()
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest)
        }

        @Test
        fun `스케쥴 생성 실패 - 시작 시간이 종료 시간 이후`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 2,
                startTime = "2099-12-01T12:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf()
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest)
        }

        // 따로 실행하면 위의 테스트에서 생성된 회의가 없어 실패할 수 있음
        @Test
        fun `스케쥴 생성 실패 - 시작 시간이 현재 시간 이전`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 2,
                startTime = "2023-12-01T10:00:00",
                endTime = "2023-12-01T11:00:00",
                participants = listOf(10, 11)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest)
        }

        @Test
        fun `스케쥴 생성 실패 - 존재하지 않는 회의실을 선택`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 4,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(10, 11)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound)
        }

        @Test
        fun `스케쥴 생성 실패 - 존재하지 않는 유저를 선택`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 2,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(10, 12, 99)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound)
        }
    }


    @Nested
    inner class GetSchedule {
        @Test
        fun `스케쥴 조회 성공`() {
            val request = CreateScheduleRequest(
                name = "회의",
                roomId = 1,
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
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
                startTime = "2099-12-01T10:00:00",
                endTime = "2099-12-01T11:00:00",
                participants = listOf(1, 2, 3)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk)
                .andDo {
                    val resultString = it.response.contentAsString
                    val result = objectMapper.readValue(resultString, CreateScheduleResponse::class.java)

                    val updateRequest = UpdateScheduleRequest(
                        name = "회의 변경",
                        roomId = 1,
                        startTime = "2099-12-01T10:30:00",
                        endTime = "2099-12-01T11:30:00",
                        participants = listOf(1, 2, 3)
                    )

                    mockMvc.perform(put("/schedules/${result.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                        .andExpect(status().isOk)
                }
        }

        @Test
        fun `스케쥴 업데이트 실패 - 다른 회의와 시간이 겹침`() {
            val request = CreateScheduleRequest(
                name = "회의 2",
                roomId = 2,
                startTime = "2099-12-01T13:00:00",
                endTime = "2099-12-01T14:00:00",
                participants = listOf(4, 5, 6)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk)
                .andDo {
                    val resultString = it.response.contentAsString
                    val createScheduleResult = objectMapper.readValue(resultString, CreateScheduleResponse::class.java)

                    val request2 = CreateScheduleRequest(
                        name = "회의 3",
                        roomId = 2,
                        startTime = "2099-12-01T15:00:00",
                        endTime = "2099-12-01T16:00:00",
                        participants = listOf(7, 8, 9)
                    )

                    mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                        .andExpect(status().isOk)

                    val updateRequest = UpdateScheduleRequest(
                        name = "회의 2 변경",
                        roomId = 2,
                        startTime = "2099-12-01T15:30:00",
                        endTime = "2099-12-01T16:30:00",
                        participants = listOf(4, 5, 6)
                    )

                    mockMvc.perform(put("/schedules/${createScheduleResult.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                        .andExpect(status().isConflict)
                }
        }

        @Test
        fun `스케쥴 업데이트 실패 - 특정 인원이 변경 시간에 회의가 존재`() {
            val request = CreateScheduleRequest(
                name = "회의 2",
                roomId = 2,
                startTime = "2099-12-01T13:00:00",
                endTime = "2099-12-01T14:00:00",
                participants = listOf(4, 5, 6)
            )

            mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk)
                .andDo {
                    val resultString = it.response.contentAsString
                    val createScheduleResult = objectMapper.readValue(resultString, CreateScheduleResponse::class.java)

                    val request2 = CreateScheduleRequest(
                        name = "회의 3",
                        roomId = 2,
                        startTime = "2099-12-01T15:00:00",
                        endTime = "2099-12-01T16:00:00",
                        participants = listOf(7, 8, 9)
                    )

                    mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                        .andExpect(status().isOk)

                    val updateRequest = UpdateScheduleRequest(
                        name = "회의 2 변경",
                        roomId = 3,
                        startTime = "2099-12-01T15:30:00",
                        endTime = "2099-12-01T16:30:00",
                        participants = listOf(7, 5, 6)
                    )

                    mockMvc.perform(put("/schedules/${createScheduleResult.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                        .andExpect(status().isConflict)
                }
        }
    }
}