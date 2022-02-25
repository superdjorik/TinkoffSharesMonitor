import kotlinx.coroutines.delay
import patterns.secondDayGrows
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime


suspend fun main() {
//    val path = "D:\\YandexDisk\\temp\\Tinkoff"
    val dateTime = LocalDate.now()

    suspend fun <T> retryIO(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 1_000,
        maxDelay: Long = 30_000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: Exception) {
                println(e.toString())
                File("${LocalDate.now()}_error.txt")
                    .appendText("${LocalDateTime.now()} \nError: ${e}\n")
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block()
    }


    val grows = retryIO { secondDayGrows() }


    File("${dateTime}_Растут.txt").writeText(grows.toString())
    File("log.txt").appendText("${LocalDateTime.now()} Finished successfull\n")

}
