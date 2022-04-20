package mario.hany.currency.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import mario.hany.currency.domain.usecase.convert.ConvertUseCase
import mario.hany.currency.domain.usecase.history.HistoryUseCase

class DetailsViewModel(
    private val historyUseCase: HistoryUseCase,
    private val convertUseCase: ConvertUseCase
) : ViewModel() {
    private val _historyViewState: MutableLiveData<HistoryViewState> by lazy { MutableLiveData() }
    val historyViewState: LiveData<HistoryViewState> = _historyViewState

    fun getHistoryList() = viewModelScope.launch(IO) {
        _historyViewState.apply {
            postValue(HistoryViewState.Loading)
            postValue(historyUseCase.getRatesHistoryList())
        }
    }

    fun getSuggestedList(baseCurr: String) {
        _historyViewState.apply {
            value = HistoryViewState.Loading
            value = convertUseCase.getTop10Rates(baseCurr)
        }
    }

}