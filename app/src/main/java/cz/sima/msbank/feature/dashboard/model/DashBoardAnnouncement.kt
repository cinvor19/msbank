package cz.sima.msbank.feature.dashboard.model

/**
 * Created by Michal Šíma on 21.06.2020.
 */
data class DashBoardAnnouncement(val id: String, val title: String, val message: String) :
    DashBoardItem {

    override fun getItemType(): DashBoardItemType {
        return DashBoardItemType.ANNOUNCEMENT
    }

    override fun areItemsSame(otherItem: DashBoardItem): Boolean {
        return id == (otherItem as? DashBoardAnnouncement)?.id
    }

    override fun areContentsSame(otherItem: DashBoardItem): Boolean {
        return message == (otherItem as? DashBoardAnnouncement)?.message &&
                title == (otherItem as? DashBoardAnnouncement)?.title
    }
}
