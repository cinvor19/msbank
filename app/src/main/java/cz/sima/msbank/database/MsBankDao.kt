package cz.sima.msbank.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Michal Šíma on 24.06.2020.
 */
@Dao
interface MsBankDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: TransactionDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transaction: List<TransactionDb>): Completable

    @Query("SELECT * FROM `transaction`")
    fun getTransactions(): Single<List<TransactionDb>>
}
