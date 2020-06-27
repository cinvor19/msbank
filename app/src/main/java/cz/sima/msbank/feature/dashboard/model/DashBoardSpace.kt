package cz.sima.msbank.feature.dashboard.model

/**
 * Created by Michal Šíma on 27.06.2020.
 */
data class DashBoardSpace(override val order: Int = Int.MAX_VALUE) : DashBoardItem {

    override fun getItemType(): DashBoardItemType {
        return DashBoardItemType.SPACE
    }

    override fun areItemsSame(otherItem: DashBoardItem): Boolean {
        return this == otherItem
    }

    override fun areContentsSame(otherItem: DashBoardItem): Boolean {
        return true
    }
}
