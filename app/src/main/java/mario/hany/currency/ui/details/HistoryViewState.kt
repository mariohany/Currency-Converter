package mario.hany.currency.ui.details

import mario.hany.currency.data.local.RateHistoryEntity

sealed class HistoryViewState {
    object Loading: HistoryViewState()
    class History(val value:List<RateHistoryEntity>): HistoryViewState()
    class Suggested(val result: List<RateHistoryEntity>) : HistoryViewState()
    class Error(val msg:String?): HistoryViewState()
}
