import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.HttpClient
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import stocksdata.GetStocks
import stocksdata.Stock

// Функция выдаёт список всех имеющихся акций
suspend fun getAllStocks(): List<Stock> {
    // kotlinx_serializer
    val client = HttpClient(CIO) {
        install(JsonFeature){
           serializer = KotlinxSerializer(Json)
        }
    }
    val token = sandboxToken // Токен Тинькова
    val sandboxUrl = "https://api-invest.tinkoff.ru/openapi/sandbox/"
//    val sandboxUrl = "https://api-invest.tinkoff.ru/openapi/"
    val allStocksUrl = "market/stocks"

    val getStocks: HttpResponse = client.get(sandboxUrl.plus(allStocksUrl)){
        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
    client.close()
//    val format = Json { prettyPrint = true }
    val getStocksText = getStocks.readText().toString()
    val getStocksJson = Json.decodeFromString<GetStocks>(getStocksText)
    val listStocks: List<Stock> = getStocksJson.payload.instruments

    return listStocks
}
