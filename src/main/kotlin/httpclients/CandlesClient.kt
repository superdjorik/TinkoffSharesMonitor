import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.HttpClient
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import stocksdata.*
//import stocksdata.Instruments
import java.time.LocalDate

// Функция выдает свечи, в запросе figi акции
suspend fun ListOfCandles(figi: String): List<CandlesInPayload> {
    // kotlinx_serializer
    val client = HttpClient(CIO) {
        install(JsonFeature){
            serializer = KotlinxSerializer(Json)
        }
    }
    val token = sandboxToken // Токен Тинькова
    val sandboxUrl = "https://api-invest.tinkoff.ru/openapi/sandbox/"
    val candlesUrl = "market/candles?"
//    val time: String = "T12:01:00%2B00:00"
//    val today = LocalDate.now().minusDays(1).toString()+time
//    val yesterday = LocalDate.now().minusDays(3).toString()+time
    val from = getWorkDays().first()
    val to = getWorkDays().last()

    val getCandles = GetCandles(figi,from,to,"day")
    val getCandlesUrl = "figi=${getCandles.figi}&from=${getCandles.from}&to=${getCandles.to}&interval=${getCandles.interval}"
    val getCandlesReruest: HttpResponse = client.get(sandboxUrl.plus(candlesUrl).plus(getCandlesUrl)){
        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
//    println(getCandlesReruest.call)
    client.close()
    val getCandlesText = getCandlesReruest.readText().toString()
    val getCandlesJson = Json.decodeFromString<ResponseCandles>(getCandlesText)
//    println(getCandlesJson)
    val listCandles: List<CandlesInPayload> = getCandlesJson.payload.candles

    return listCandles
}
