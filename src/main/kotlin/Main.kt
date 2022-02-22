import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.HttpClient
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import patterns.secondDayGrows
import stocksdata.GetStocks
import stocksdata.Stock
import java.io.File
import java.net.ConnectException
import java.time.LocalDate
import java.time.LocalDateTime


suspend fun main() {

    val path = "D:\\YandexDisk\\temp\\Tinkoff"
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
                File(path, "${LocalDate.now()}_error.txt")
                    .appendText("${LocalDateTime.now()} \nError: ${e.toString()}\n")
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }

    val grows = retryIO { secondDayGrows() }
    File(path, "${dateTime}_Растут.txt").writeText(grows.toString())
    File(path, "log.txt").appendText("\n${dateTime} Finished successfull")

}
