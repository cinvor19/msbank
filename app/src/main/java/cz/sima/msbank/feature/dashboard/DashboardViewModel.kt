package cz.sima.msbank.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.feature.cards.Card
import cz.sima.msbank.feature.cards.CardType
import cz.sima.msbank.feature.dashboard.model.DashBoardAccount
import cz.sima.msbank.feature.dashboard.model.DashBoardAnnouncement
import cz.sima.msbank.feature.dashboard.model.DashBoardCreditCard
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import cz.sima.msbank.feature.dashboard.model.DashBoardPromo
import cz.sima.msbank.shared.BankAccount
import java.math.BigDecimal

class DashboardViewModel : BaseViewModel() {

    private val dashBoardItems: MutableLiveData<List<DashBoardItem>> = MutableLiveData()

    fun getDashBoardItems(): LiveData<List<DashBoardItem>> = dashBoardItems
    fun fetchData() {
        dashBoardItems.value = mockItems()
    }

    private fun mockItems(): List<DashBoardItem> {
        val list = mutableListOf<DashBoardItem>()
        list.add(
            DashBoardAnnouncement(
                "1",
                "Maintenance warning",
                "There will be maintenance next weekend from 5AM to 10PM"
            )
        )
        list.add(
            DashBoardPromo(
                "11",
                "Ultra special promotion",
                "Special saving account just for you!"
            )
        )
        list.add(
            DashBoardAccount(
                "234", BankAccount(
                    number = "1199210244",
                    bankCode = "0666"
                ), BigDecimal("64263.4")
            )
        )
        list.add(
            DashBoardCreditCard(
                "567", Card("2", "1234123412341234", CardType.VISA), BigDecimal("13323.2")
            )
        )
        return list
    }

    fun onFabButtonClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }
}
