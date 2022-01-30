package stocksdata
import kotlinx.serialization.Serializable

@Serializable
data class GetCandles(
    val figi: String = "BBG002293PJ4",
    val from: String = "2022-01-28T04:00:00+3",
    val to: String = "2022-01-28T04:00:00+3",
    val interval: String = "day"
)

@Serializable
data class ResponseCandles(
    val trackingId: String,
    val status: String,
    val payload: PayloadCandles,
)

@Serializable
data class PayloadCandles(
    val figi: String,
    val interval: String,
    val candles: List<CandlesInPayload>
)

@Serializable
data class CandlesInPayload(
    val figi: String,
    val interval: String,
    val o: Double,
    val c: Double,
    val h: Double,
    val l: Double,
    val v: Double,
    val time: String
)