package cz.sima.msbank.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.sima.msbank.api.TransactionResponse

/**
 * Created by Michal Šíma on 24.06.2020.
 */
@Entity(tableName = "transaction")
data class TransactionDb(
    @PrimaryKey val id: String,
    val accountId: String,
    val amount: String,
    val counterPart: String
) {
    companion object {
        fun fromApiResponse(
            transactionResponse: TransactionResponse,
            accountId: String
        ): TransactionDb {
            return TransactionDb(
                transactionResponse.id,
                accountId,
                transactionResponse.amount,
                transactionResponse.counterPart
            )
        }
    }
}

@Entity(tableName = "account")
data class DashBoardAccountDb(
    @PrimaryKey val id: String,
    val accountNumber: String,
    val balance: String,
    val order: Int
)

@Entity(tableName = "announcement")
class DashBoardAnnouncementDb(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val order: Int
)

@Entity(tableName = "creditCard")
data class DashBoardCreditCardDb(
    @PrimaryKey val id: String,
    val cardNumber: String,
    val balance: String,
    val order: Int
)

@Entity(tableName = "promo")
data class DashBoardPromoDb(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val order: Int
)
