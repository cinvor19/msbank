package cz.sima.msbank.feature.dashboard

import cz.sima.msbank.api.ApiService
import cz.sima.msbank.feature.dashboard.model.DashBoardAccount
import cz.sima.msbank.feature.dashboard.model.DashBoardAnnouncement
import cz.sima.msbank.feature.dashboard.model.DashBoardCreditCard
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import cz.sima.msbank.feature.dashboard.model.DashBoardPromo
import io.reactivex.Single

/**
 * Created by Michal Šíma on 23.06.2020.
 */
class DashBoardRepository(private val apiService: ApiService) {

    fun fetchDashBoard(): Single<List<DashBoardItem>> {
        return apiService.fetchDashBoard()
            .map { response ->
                val list = mutableListOf<DashBoardItem>()
                list.addAll(response.promos.map { DashBoardPromo.fromApi(it) })
                list.addAll(response.creditCards.map { DashBoardCreditCard.fromApi(it) })
                list.addAll(response.accounts.map { DashBoardAccount.fromApi(it) })
                list.addAll(response.announcements.map { DashBoardAnnouncement.fromApi(it) })
                list.shuffle()
                list
            }
    }
}
