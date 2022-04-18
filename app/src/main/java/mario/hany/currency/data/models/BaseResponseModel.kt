package mario.hany.currency.data.models


data class BaseResponseModel(
    val base: String?,
    val date: String?,
    val error: Error?,
    val rates: Rates?,
    val success: Boolean?,
    val timestamp: Int?
)