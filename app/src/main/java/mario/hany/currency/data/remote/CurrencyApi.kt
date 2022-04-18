package mario.hany.currency.data.remote

import mario.hany.currency.data.models.BaseResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getRate(
        @Query("base") fromCurrency: String,
        @Query("symbols") toCurrency: String,
    ):BaseResponseModel

}