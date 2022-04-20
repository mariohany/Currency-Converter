package mario.hany.currency.domain.repo

import mario.hany.currency.data.local.RateHistoryEntity
import mario.hany.currency.data.models.BaseResponseModel

interface ICurrencyRepo {
    suspend fun getRate(): BaseResponseModel
    suspend fun insertRateHistory(model: RateHistoryEntity)
    suspend fun getAllRatesHistory(): List<RateHistoryEntity>
}