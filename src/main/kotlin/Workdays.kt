import java.time.DayOfWeek
import java.time.LocalDate

// Функция, которая берет системную дату и возвращает список "from,to"
// из последних рабочих дней, для формирования запроса свечей.
fun getWorkDays(): List<String> {
    val time: String = "T07:01:00%2B00:00"
    val today = LocalDate.now()
    val todayWeekDay = today.dayOfWeek
    lateinit var lastDay: LocalDate
    lateinit var beforeLastDay: LocalDate
    when (todayWeekDay) {
        DayOfWeek.SATURDAY -> {
            lastDay = today.minusDays(1)
            beforeLastDay = lastDay.minusDays(2)
        }
        DayOfWeek.SUNDAY -> {
            lastDay = today.minusDays(2)
            beforeLastDay = lastDay.minusDays(2)
        }
        DayOfWeek.MONDAY -> {
            lastDay = today.minusDays(2)
            beforeLastDay = lastDay.minusDays(3)
        }
        DayOfWeek.TUESDAY -> {
//            lastDay = today.minusDays(1)
            lastDay = today.minusDays(1)
            beforeLastDay = lastDay.minusDays(4)
        }
        else -> {
            lastDay = today.minusDays(1)
            beforeLastDay = lastDay.minusDays(2)
        }
    }
    val to = lastDay.toString() + time
    val from = beforeLastDay.toString() + time
    return listOf(from, to)
}
