package schedule.exception

sealed class BusinessException(message: String): Exception(message)

class ResourceNotFoundException(message: String): BusinessException(message)
class InvalidRequestException(message: String): BusinessException(message)
class AlreadyReservedException(message: String): BusinessException(message)
class RoomCapacityExceededException(message: String): BusinessException(message)
