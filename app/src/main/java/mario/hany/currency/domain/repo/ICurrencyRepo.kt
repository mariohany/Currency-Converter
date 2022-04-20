package mario.hany.currency.domain.repo

import mario.hany.currency.data.models.BaseResponseModel

interface ICurrencyRepo {
    suspend fun getRate(): BaseResponseModel
}