import kotlinx.coroutines.delay
import patterns.noFalling
import patterns.secondDayFalling
import stocksdata.CandlesInPayload
import stocksdata.PayloadCandles
import java.io.File
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.*
import java.time.Instant.now
import java.time.format.DateTimeFormatter

suspend fun main(){

    val allStocks = getAllStocks()
    val stockSize = allStocks.size-1
//    val grows = secondDayGrows(stockSize)
//    val falls = secondDayFalling(stockSize)
    val noFalls = noFalling(stockSize)
//    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d_HH-mm"))
//
//    println(dateTime)
////    File("D:\\YandexDisk\\temp\\3DayRocket","${dateTime}.txt").writeText(grows.toString())



}