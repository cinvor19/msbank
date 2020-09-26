package cz.sima.msbank.feature.settings

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.feature.settings.model.SettingOption
import cz.sima.msbank.feature.settings.model.SettingsHeader
import cz.sima.msbank.feature.settings.model.SettingsItem

class SettingsViewModel : BaseViewModel() {

    private val settingsItems: MutableLiveData<List<SettingsItem>> = MutableLiveData()

    fun getSettingsItems(): LiveData<List<SettingsItem>> = settingsItems


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun buildSettingsMenu() {
        settingsItems.value =
            listOf(
                SettingsHeader.SECTION1,
                SettingOption.SUPPORT_CHAT,
                SettingOption.SETTINGS1,
                SettingOption.SETTINGS2,
                SettingOption.SETTINGS3,
                SettingOption.SETTINGS4,
                SettingOption.SETTINGS5,
                SettingsHeader.SECTION2,
                SettingOption.C,
                SettingOption.D,
                SettingsHeader.SECTION3,
                SettingOption.E,
                SettingOption.F
            )
    }

    fun onSettingItemClicked(clickedItem: SettingsItem) {
        when (clickedItem as? SettingOption) {
            SettingOption.SUPPORT_CHAT -> {
                navigate(R.id.action_navigation_settings_to_supportChatFragment)
            }
        }
    }
}
