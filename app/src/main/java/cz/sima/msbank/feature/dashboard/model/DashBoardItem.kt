package cz.sima.msbank.feature.dashboard.model

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Michal Šíma on 20.06.2020.
 */
interface DashBoardItem {

    val order: Int

    fun getItemType(): DashBoardItemType

    fun areItemsSame(otherItem: DashBoardItem): Boolean
    fun areContentsSame(otherItem: DashBoardItem): Boolean

}

enum class DashBoardItemType(val viewType: Int) {
    ACCOUNT(1),
    CREDIT(2),
    PROMO(3),
    ANNOUNCEMENT(4);

    companion object {
        fun fromInt(value: Int): DashBoardItemType {
            return values().find { it.viewType == value }
                ?: throw IllegalArgumentException("Value not found in DashBoardItemType")
        }
    }
}

object DashBoardDiffUtil : DiffUtil.ItemCallback<DashBoardItem>() {

    override fun areItemsTheSame(oldItem: DashBoardItem, newItem: DashBoardItem): Boolean {
        return newItem.areItemsSame(oldItem)
    }

    override fun areContentsTheSame(oldItem: DashBoardItem, newItem: DashBoardItem): Boolean {
        return newItem.areContentsSame(oldItem)
    }
}
