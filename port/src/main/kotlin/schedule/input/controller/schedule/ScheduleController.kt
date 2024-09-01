package schedule.input.controller.schedule

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import schedule.application.ScheduleApplicationService
import schedule.input.controller.schedule.request.CreateScheduleRequest
import schedule.input.controller.schedule.request.UpdateScheduleRequest
import schedule.input.controller.schedule.response.CreateScheduleResponse
import schedule.input.controller.schedule.response.GetScheduleResponse
import schedule.input.controller.schedule.response.UpdateScheduleResponse

@RestController
@RequestMapping("/schedules")
@Tag(name = "Schedule Controller", description = "APIs for get and managing schedules")
class ScheduleController(
    private val scheduleApplicationService: ScheduleApplicationService,
) {

    @Operation(summary = "Create a new schedule", description = "Creates a new schedule with the provided details.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Schedule created successfully",
            content = [Content(schema = Schema(implementation = CreateScheduleResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "409", description = "Schedule conflict",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", description = "Resource not found",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PostMapping
    fun createSchedule(
        @RequestBody request: CreateScheduleRequest
    ): ResponseEntity<CreateScheduleResponse> {
        val result = scheduleApplicationService.create(
            command = request.toCommand()
        )

        return ResponseEntity.ok(
            CreateScheduleResponse.of(result)
        )
    }

    @Operation(summary = "Get a schedule by ID", description = "Fetches a schedule based on the given ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Schedule retrieved successfully",
            content = [Content(schema = Schema(implementation = GetScheduleResponse::class))]),
        ApiResponse(responseCode = "404", description = "Schedule not found",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @GetMapping("/{id}")
    fun getSchedules(@PathVariable id: Long): ResponseEntity<GetScheduleResponse> {
        val result = scheduleApplicationService.getSchedule(id)

        return ResponseEntity.ok(
            GetScheduleResponse.of(result)
        )
    }

    @Operation(summary = "Update a schedule by ID", description = "Updates an existing schedule based on the provided details.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Schedule updated successfully",
            content = [Content(schema = Schema(implementation = UpdateScheduleResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "409", description = "Schedule conflict",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", description = "Resource not found",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PutMapping("/{id}")
    fun updateSchedules(
        @PathVariable id: Long,
        @RequestBody request: UpdateScheduleRequest
    ): ResponseEntity<UpdateScheduleResponse> {
        val result = scheduleApplicationService.update(
            command = request.toCommand(id)
        )

        return ResponseEntity.ok(
            UpdateScheduleResponse.of(result)
        )
    }
}