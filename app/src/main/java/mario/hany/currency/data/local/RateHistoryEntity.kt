package mario.hany.currency.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RateHistoryEntity(
    val fromCur:String,
    val toCur:String,
    val fromAmount: Double,
    val toAmount: Double,
    val date:String,
    @PrimaryKey val id: Int? = null
)
