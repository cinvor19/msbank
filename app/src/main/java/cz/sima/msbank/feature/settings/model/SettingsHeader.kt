package cz.sima.msbank.feature.settings.model

/**
 * Created by Michal Šíma on 26.09.2020.
 */
enum class SettingsHeader : SettingsItem {

    SECTION1, SECTION2, SECTION3;

    override fun getItemType(): SettingsItemType {
        return SettingsItemType.HEADER
    }

    override fun areItemsSame(otherItem: SettingsItem): Boolean {
        return this == otherItem
    }

    override fun areContentsSame(otherItem: SettingsItem): Boolean {
        return true
    }
}
