package cz.sima.msbank.feature.pin

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel

/**
 * Created by Michal Šíma on 13.06.2020.
 */
class PinViewModel : BaseViewModel() {

    fun onBtnClicked() {
        navigate(R.id.action_pinFragment_to_navigation_dashboard)
    }
}