package mario.hany.currency.ui.converter

sealed class ConvertViewState {
    object Loading: ConvertViewState()
    object Rates: ConvertViewState()
    class SuccessFrom(val value:Double): ConvertViewState()
    class SuccessTo(val value:Double): ConvertViewState()
    class Error(val msg:String?): ConvertViewState()
}