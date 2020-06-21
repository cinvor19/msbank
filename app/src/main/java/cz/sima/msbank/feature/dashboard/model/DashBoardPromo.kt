package cz.sima.msbank.feature.dashboard.model

/**
 * Created by Michal Šíma on 21.06.2020.
 */
data class DashBoardPromo(val id: String, val title: String, val message: String) :
    DashBoardItem {

    override fun getItemType(): DashBoardItemType {
        return DashBoardItemType.PROMO
    }

    override fun areItemsSame(otherItem: DashBoardItem): Boolean {
        return id == (otherItem as? DashBoardPromo)?.id
    }

    override fun areContentsSame(otherItem: DashBoardItem): Boolean {
        return message == (otherItem as? DashBoardPromo)?.message &&
                title == (otherItem as? DashBoardPromo)?.title
    }
}
