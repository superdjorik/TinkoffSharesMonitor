package stocksdata

//import kotlinx.serialization.Serializable
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.StructureKind

@Serializable
data class GetStocks(
    val trackingId : String,
    val payload: Payload,
    val status: String
)

@Serializable
data class Payload(
    val instruments: List<Stock>,
    val total: Int
)

@Serializable
data class Stock(
    val figi: String?,
    val ticker: String?,
    val isin: String?, // Map<String, String>,
    val minPriceIncrement: Double? = 0.0,
    val lot: Int?,
    val minQuantity: Int? = 0,
    val currency: String?,
    val name: String?,
    val type: String?
)

