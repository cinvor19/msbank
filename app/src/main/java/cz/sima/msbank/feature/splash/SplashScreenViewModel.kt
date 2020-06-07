package cz.sima.msbank.feature.splash

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel

/**
 * Created by Michal Šíma on 05.06.2020.
 */
class SplashScreenViewModel : BaseViewModel() {

    fun onBtnClicked() {
        navigate(R.id.action_fragment_splash_to_navigation_dashboard)
    }
}