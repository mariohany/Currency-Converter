package mario.hany.currency.data.repo

import mario.hany.currency.data.models.BaseResponseModel
import mario.hany.currency.data.remote.CurrencyApi
import mario.hany.currency.domain.repo.ICurrencyRepo

class CurrencyRepo(val api: CurrencyApi): ICurrencyRepo {
    override suspend fun getRate(fromCurr: String, toCurr: String): BaseResponseModel =
        api.getRate(fromCurr, toCurr)
}