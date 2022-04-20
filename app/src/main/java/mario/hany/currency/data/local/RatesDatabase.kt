package mario.hany.currency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RateHistoryEntity::class], version = 1)
abstract class RatesDatabase: RoomDatabase() {
    abstract val dao: RateHistoryDao
}