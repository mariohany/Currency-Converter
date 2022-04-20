package mario.hany.currency.data.remote

import mario.hany.currency.data.models.BaseResponseModel
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest")
    suspend fun getRate():BaseResponseModel

}