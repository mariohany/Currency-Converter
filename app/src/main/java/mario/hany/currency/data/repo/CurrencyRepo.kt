package mario.hany.currency.data.repo

import mario.hany.currency.data.local.RateHistoryEntity
import mario.hany.currency.data.local.RatesDatabase
import mario.hany.currency.data.models.BaseResponseModel
import mario.hany.currency.data.remote.CurrencyApi
import mario.hany.currency.domain.repo.ICurrencyRepo

class CurrencyRepo(private val api: CurrencyApi, db: RatesDatabase): ICurrencyRepo {
    private val dao = db.dao

    override suspend fun getRate(): BaseResponseModel = api.getRate()

    override suspend fun insertRateHistory(model: RateHistoryEntity) = dao.insertRateHistory(model)

    override suspend fun getAllRatesHistory(): List<RateHistoryEntity> = dao.getRateHistory()
}