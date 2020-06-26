package cz.sima.msbank.feature.dashboard.model

import cz.sima.msbank.api.DashBoardCreditCardApi
import cz.sima.msbank.database.DashBoardCreditCardDb
import java.math.BigDecimal

/**
 * Created by Michal Šíma on 21.06.2020.
 */
data class DashBoardCreditCard(
    val id: String,
    val cardNumber: String,
    val balance: BigDecimal,
    override val order: Int
) : DashBoardItem {

    companion object {
        fun fromApi(apiObject: DashBoardCreditCardApi, order: Int): DashBoardCreditCard {
            return DashBoardCreditCard(
                apiObject.id,
                apiObject.cardNumber,
                BigDecimal(apiObject.balance),
                order
            )
        }

        fun fromDb(dbObject: DashBoardCreditCardDb): DashBoardCreditCard {
            return DashBoardCreditCard(
                dbObject.id,
                dbObject.cardNumber,
                BigDecimal(dbObject.balance),
                dbObject.order
            )
        }
    }

    fun toDb(): DashBoardCreditCardDb {
        return DashBoardCreditCardDb(id, cardNumber, balance.toPlainString(), order)
    }

    override fun getItemType(): DashBoardItemType {
        return DashBoardItemType.CREDIT
    }

    override fun areItemsSame(otherItem: DashBoardItem): Boolean {
        return id == (otherItem as? DashBoardCreditCard)?.id
    }

    override fun areContentsSame(otherItem: DashBoardItem): Boolean {
        return cardNumber == (otherItem as? DashBoardCreditCard)?.cardNumber &&
                balance == (otherItem as? DashBoardCreditCard)?.balance
    }
}
