package cz.sima.msbank.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Michal Šíma on 24.06.2020.
 */
@Dao
interface MsBankDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: TransactionDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transaction: List<TransactionDb>): Completable

    @Query("SELECT * FROM `transaction` where accountId==:accountId")
    fun getTransactions(accountId: String): Flowable<List<TransactionDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashBoardPromo(promos: List<DashBoardPromoDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashBoardAccount(promos: List<DashBoardAccountDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashBoardCreditCard(promos: List<DashBoardCreditCardDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashBoardAnnouncement(promos: List<DashBoardAnnouncementDb>): Completable

    @Query("SELECT * FROM promo")
    fun fetchDashBoardPromo(): Flowable<List<DashBoardPromoDb>>

    @Query("SELECT * FROM announcement")
    fun fetchDashBoardAnnouncement(): Flowable<List<DashBoardAnnouncementDb>>

    @Query("SELECT * FROM creditCard")
    fun fetchDashBoardCreditCard(): Flowable<List<DashBoardCreditCardDb>>

    @Query("SELECT * FROM account")
    fun fetchDashBoardAccount(): Flowable<List<DashBoardAccountDb>>
}
