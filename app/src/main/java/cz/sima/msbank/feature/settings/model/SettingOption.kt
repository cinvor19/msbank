package cz.sima.msbank.feature.settings.model

/**
 * Created by Michal Šíma on 26.09.2020.
 */
enum class SettingOption : SettingsItem {
    SUPPORT_CHAT, SETTINGS1, SETTINGS2, SETTINGS3, SETTINGS4, SETTINGS5, C, D, E, F;

    override fun getItemType(): SettingsItemType {
        return SettingsItemType.ITEM
    }

    override fun areItemsSame(otherItem: SettingsItem): Boolean {
        return this == otherItem
    }

    override fun areContentsSame(otherItem: SettingsItem): Boolean {
        return true
    }
}
