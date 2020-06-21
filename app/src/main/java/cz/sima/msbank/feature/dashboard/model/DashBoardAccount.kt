package cz.sima.msbank.feature.dashboard.model

import cz.sima.msbank.shared.BankAccount
import java.math.BigDecimal

/**
 * Created by Michal Šíma on 21.06.2020.
 */
data class DashBoardAccount(val id: String, val account: BankAccount, val balance : BigDecimal) :
    DashBoardItem {

    override fun getItemType(): DashBoardItemType {
        return DashBoardItemType.ACCOUNT
    }

    override fun areItemsSame(otherItem: DashBoardItem): Boolean {
        return id == (otherItem as? DashBoardAccount)?.id
    }

    override fun areContentsSame(otherItem: DashBoardItem): Boolean {
        return account == (otherItem as? DashBoardAccount)?.account &&
                balance == (otherItem as? DashBoardAccount)?.balance
    }
}
