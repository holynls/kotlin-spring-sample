package schedule

import io.github.serpro69.kfaker.Faker
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import schedule.room.model.Room
import schedule.room.writer.RoomWriter
import schedule.user.model.User
import schedule.user.writer.UserWriter

@Component
class DataInitializer(
    private val roomWriter: RoomWriter,
    private val userWriter: UserWriter,
) : ApplicationRunner {

    private val faker = Faker()
    private val roomCapacities = listOf(3, 5, 10)

    override fun run(args: ApplicationArguments?) {
        val rooms = listOf(
            Room(
                name = "Room 1",
                capacity = 3,
            ),
            Room(
                name = "Room 2",
                capacity = 5,
            ),
            Room(
                name = "Room 3",
                capacity = 10,
            ),
        )

        roomWriter.createRooms(rooms)

        val users = List(20) {
            User(
                name = faker.name.firstName(),
            )
        }

        userWriter.createUsers(users)
    }
}