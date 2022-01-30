package patterns

import ListOfCandles
import getAllStocks
import kotlinx.coroutines.delay
import stocksdata.CandlesInPayload
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Функция мониторит акции, которые растут в течении двух дней
// в конце пишет в файл
suspend fun secondDayGrows(stockSize: Int = 30): MutableList<String>{

    val allStocks = getAllStocks()
    val endValue: Int = stockSize
    val stocksList = allStocks.subList(0,endValue)
    var successStocks: MutableList<String> = mutableListOf("2DayGrows Stocks:\n")
    for (i in stocksList){
//        println(i)
        var firstCandle = ListOfCandles(i.figi.toString())
//        println(firstCandle)
        if (firstCandle.size==2) {
            delay(10)
            var lastDay: CandlesInPayload = firstCandle[1]
            var dayBefore: CandlesInPayload = firstCandle[0]
            var percentLast = (lastDay.h - lastDay.l)*0.2
            var percentBefore = (dayBefore.h - dayBefore.l)*0.2

//           println("before: ${dayBefore.time}, last: ${lastDay.time}")

            if( ((lastDay.o) <= (lastDay.l+percentLast)) && ((lastDay.c) > (lastDay.o)) && ((lastDay.c) >= (lastDay.h-percentLast))
                && ((dayBefore.o) <= (dayBefore.l+percentBefore)) && ((dayBefore.c) > (dayBefore.o)) && ((dayBefore.c) >= (dayBefore.h-percentBefore))
                && ((dayBefore.o) <= (lastDay.l))) {
                println("${ i.ticker.toString() } , $dayBefore, $lastDay")
                successStocks.add("\n ${i.ticker.toString()} \n${i.name}\n "+
                        "${dayBefore.time},o:${dayBefore.o}, c:${dayBefore.c}, l:${dayBefore.l}, h:${dayBefore.h}\n"+
                        "${lastDay.time}, o:${lastDay.o}, c:${lastDay.c}, l:${lastDay.l}, h:${lastDay.h}")
            }
        }
    }

//    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d_H-m-ss"))
    val dateTime = LocalDate.now()
    File("D:\\YandexDisk\\temp\\Tinkoff","${dateTime}_Растут.txt").writeText(successStocks.toString())
    return successStocks
}