package mario.hany.currency.ui.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import mario.hany.currency.domain.usecase.convert.ConvertUseCase

class ConvertViewModel(private val convertUseCase: ConvertUseCase) : ViewModel() {
    private val _viewState: MutableLiveData<ConvertViewState> by lazy { MutableLiveData() }
    val viewState: LiveData<ConvertViewState> = _viewState

    fun calculateRate(from: String, to: String, amount:Double, isFromText: Boolean = false) {
        _viewState.run{
            value = ConvertViewState.Loading
            value = convertUseCase.invoke(from, to, amount, isFromText)
        }
    }

    fun getRates() = viewModelScope.launch(IO) {
        _viewState.run {
            postValue(convertUseCase.getRates())
        }
    }

}