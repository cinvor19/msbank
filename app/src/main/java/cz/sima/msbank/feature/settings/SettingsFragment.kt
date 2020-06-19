package cz.sima.msbank.feature.settings

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentSplashBinding

class SettingsFragment :
    BaseVMFragment<FragmentSplashBinding, SettingsViewModel>(SettingsViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_settings

    override fun hasBottomNav() = true
}
