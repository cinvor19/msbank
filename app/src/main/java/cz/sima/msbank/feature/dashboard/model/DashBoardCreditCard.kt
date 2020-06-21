package cz.sima.msbank.feature.dashboard.model

import cz.sima.msbank.feature.cards.Card
import java.math.BigDecimal

/**
 * Created by Michal Šíma on 21.06.2020.
 */
data class DashBoardCreditCard(val id: String, val creditCard: Card, val balance: BigDecimal) :
    DashBoardItem {

    override fun getItemType(): DashBoardItemType {
        return DashBoardItemType.CREDIT
    }

    override fun areItemsSame(otherItem: DashBoardItem): Boolean {
        return id == (otherItem as? DashBoardCreditCard)?.id
    }

    override fun areContentsSame(otherItem: DashBoardItem): Boolean {
        return creditCard == (otherItem as? DashBoardCreditCard)?.creditCard &&
                balance == (otherItem as? DashBoardCreditCard)?.balance
    }
}
