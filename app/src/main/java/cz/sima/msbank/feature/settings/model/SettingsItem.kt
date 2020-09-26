package cz.sima.msbank.feature.settings.model

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Michal Šíma on 26.09.2020.
 */
interface SettingsItem {

    fun getItemType(): SettingsItemType

    fun areItemsSame(otherItem: SettingsItem): Boolean
    fun areContentsSame(otherItem: SettingsItem): Boolean
}

enum class SettingsItemType(val viewType: Int) {
    ITEM(1),
    HEADER(2);

    companion object {
        fun fromInt(value: Int): SettingsItemType {
            return SettingsItemType.values().find { it.viewType == value }
                ?: throw IllegalArgumentException("Value not found in SettingsItemType")
        }
    }
}

object SettingsDiffUtil : DiffUtil.ItemCallback<SettingsItem>() {

    override fun areItemsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
        return newItem.areItemsSame(oldItem)
    }

    override fun areContentsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
        return newItem.areContentsSame(oldItem)
    }
}
