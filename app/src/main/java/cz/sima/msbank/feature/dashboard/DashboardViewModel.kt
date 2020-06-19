package cz.sima.msbank.feature.dashboard

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel

class DashboardViewModel : BaseViewModel() {

    fun onFabButtonClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }
}
