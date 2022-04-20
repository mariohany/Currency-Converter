package mario.hany.currency.data.models

import mario.hany.currency.data.local.RateHistoryEntity

data class HistorySection(
    val date:String,
    val convertList: List<RateHistoryEntity>
)
