package schedule.input.controller.schedule

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import schedule.exception.AlreadyReservedException
import schedule.exception.InvalidRequestException
import schedule.exception.ResourceNotFoundException
import schedule.exception.RoomCapacityExceededException

data class ErrorResponse(val error: String, val detail: String)

@ControllerAdvice(assignableTypes = [ScheduleController::class])
class ControllerExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(InvalidRequestException::class, HttpMessageNotReadableException::class)
    fun handleInvalidRequestException(exception: InvalidRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    "Invalid request data",
                    exception.message ?: "요청 데이터가 올바르지 않습니다."
                )
            )
    }

    @ExceptionHandler(AlreadyReservedException::class)
    fun handleAlreadyReservedException(exception: AlreadyReservedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                ErrorResponse(
                    "Schedule conflict",
                    exception.message ?: "선택한 회의실은 해당 시간에 이미 예약되었습니다."
                )
            )
    }

    @ExceptionHandler(RoomCapacityExceededException::class)
    fun handleRoomCapacityExceededException(exception: RoomCapacityExceededException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    "Exceeding room capacity",
                    exception.message ?: "회의실의 최대 수용 가능 인원을 초과했습니다."
                )
            )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(exception: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    "Resource not found",
                    exception.message ?: "요청한 일정 또는 회의실을 찾을 수 없습니다."
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(exception: Exception): ResponseEntity<ErrorResponse> {
        log.error("예기치 못한 오류 발생. message: ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    "Internal Server Error",
                    "서버에서 예기치 않은 오류가 발생했습니다. 나중에 다시 시도해 주세요."
                )
            )
    }
}