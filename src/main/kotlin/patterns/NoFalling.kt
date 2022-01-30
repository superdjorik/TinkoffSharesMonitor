package patterns

import ListOfCandles
import getAllStocks
import kotlinx.coroutines.delay
import stocksdata.CandlesInPayload
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Функция мониторит акции, которые не растут и не падают, хотя пробиваются вниз
// в конце пишет в файл
suspend fun noFalling(stockSize: Int = 30): MutableList<String>{

    val allStocks = getAllStocks()
    val endValue: Int = stockSize
    val stocksList = allStocks.subList(0,endValue)
    var successStocks: MutableList<String> = mutableListOf("Не растут, или не падают:\n")
    for (i in stocksList){
//        println(i)
        var firstCandle = ListOfCandles(i.figi.toString())
//        println(firstCandle)
        if (firstCandle.size==2) {
            delay(10)
            var lastDay: CandlesInPayload = firstCandle[1]
            var dayBefore: CandlesInPayload = firstCandle[0]
            var percentLast = (lastDay.h - lastDay.l)*0.94
            var percentBefore = (dayBefore.h - dayBefore.l)*0.94

//           println("before: ${dayBefore.time}, last: ${lastDay.time}")

            if( (
                        ( ((lastDay.o) >= (lastDay.l+percentLast)) && ((lastDay.c) >= (lastDay.l+percentLast)) )
                || ( ((dayBefore.o) >= (dayBefore.l+percentBefore)) &&  ((dayBefore.c) >= (dayBefore.l+percentBefore)))
                        )
                || (( ((lastDay.o) <= (lastDay.h-percentLast)) && ((lastDay.c) <= (lastDay.h-percentLast)) )
                || ( ((dayBefore.o) <= (dayBefore.h-percentBefore)) &&  ((dayBefore.c) <= (dayBefore.h-percentBefore))))
                    ) {
                println("${ i.ticker.toString() } , $dayBefore, $lastDay")
//                println("${ i.ticker.toString() } , $dayBefore, $lastDay")
                successStocks.add("\n ${i.ticker.toString()} \n${i.name}\n "+
                        "${dayBefore.time},o:${dayBefore.o}, c:${dayBefore.c}, l:${dayBefore.l}, h:${dayBefore.h}\n"+
                        "${lastDay.time}, o:${lastDay.o}, c:${lastDay.c}, l:${lastDay.l}, h:${lastDay.h}")
            }
        }
    }

//    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d_H-m-ss"))
    val dateTime = LocalDate.now()
    File("D:\\YandexDisk\\temp\\Tinkoff","${dateTime}_Не падают.txt").writeText(successStocks.toString())
    return successStocks
}