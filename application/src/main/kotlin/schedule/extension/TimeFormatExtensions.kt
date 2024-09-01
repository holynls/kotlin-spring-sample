package schedule.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toTimeFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return LocalDateTime.parse(this, formatter)
}