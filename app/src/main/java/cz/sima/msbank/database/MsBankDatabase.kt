package cz.sima.msbank.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Michal Šíma on 24.06.2020.
 */
@Database(
    entities = [TransactionDb::class,
        DashBoardAccountDb::class,
        DashBoardAnnouncementDb::class,
        DashBoardCreditCardDb::class,
        DashBoardPromoDb::class],
    version = 1,
    exportSchema = false
)
abstract class MsBankDatabase : RoomDatabase() {

    abstract fun getMsBankDao(): MsBankDao
}
