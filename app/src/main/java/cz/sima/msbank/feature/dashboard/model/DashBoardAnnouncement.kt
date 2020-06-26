package cz.sima.msbank.feature.dashboard.model

import cz.sima.msbank.api.DashBoardAnnouncementApi
import cz.sima.msbank.database.DashBoardAnnouncementDb
import cz.sima.msbank.database.DashBoardPromoDb

/**
 * Created by Michal Šíma on 21.06.2020.
 */
data class DashBoardAnnouncement(
    val id: String,
    val title: String,
    val message: String,
    override val order: Int
) : DashBoardItem {

    companion object {
        fun fromApi(apiObject: DashBoardAnnouncementApi, order: Int): DashBoardAnnouncement {
            return DashBoardAnnouncement(
                apiObject.id,
                apiObject.message,
                apiObject.title,
                order
            )
        }

        fun fromDb(dbObject: DashBoardAnnouncementDb): DashBoardAnnouncement {
            return DashBoardAnnouncement(
                dbObject.id,
                dbObject.message,
                dbObject.title,
                dbObject.order
            )
        }
    }

    fun toDb(): DashBoardAnnouncementDb {
        return DashBoardAnnouncementDb(id, title, message, order)
    }

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
