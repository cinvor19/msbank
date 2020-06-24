package cz.sima.msbank.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Michal Šíma on 24.06.2020.
 */
@Entity(tableName = "transaction")
data class TransactionDb(
    @PrimaryKey val id: String,
    val amount: String,
    val counterPart: String
)
