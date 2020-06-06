package cz.sima.msbank.dashboard

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentDashboardBinding

class DashboardFragment :
    BaseVMFragment<FragmentDashboardBinding, DashboardViewModel>(DashboardViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_dashboard

    override fun hasBottomNav() = true
}
