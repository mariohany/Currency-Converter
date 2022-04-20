package mario.hany.currency.domain.usecase.history

import mario.hany.currency.data.local.RateHistoryEntity
import mario.hany.currency.domain.repo.ICurrencyRepo
import mario.hany.currency.ui.details.HistoryViewState

class HistoryUseCase(private val repo: ICurrencyRepo) {

    suspend fun saveRateHistory(model: RateHistoryEntity) = repo.insertRateHistory(model)

    suspend fun getRatesHistoryList(): HistoryViewState {
        return try {
            HistoryViewState.History(repo.getAllRatesHistory())
        }catch (e:Exception){
            HistoryViewState.Error(e.localizedMessage ?: "Un Expected Error")
        }
    }
}