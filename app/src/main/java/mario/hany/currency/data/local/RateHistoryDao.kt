package mario.hany.currency.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RateHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRateHistory(model: RateHistoryEntity)

    @Query("SELECT * FROM RateHistoryEntity")
    suspend fun getRateHistory(): List<RateHistoryEntity>
}