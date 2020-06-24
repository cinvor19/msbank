package cz.sima.msbank.feature.dashboard

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment :
    BaseVMFragment<FragmentDashboardBinding, DashboardViewModel>(DashboardViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_dashboard

    override fun hasBottomNav() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
    }

    private fun initRecycler() {
        val adapter = DashBoardAdapter(viewModel)
        recycler_dashboards.layoutManager = LinearLayoutManager(context)

        recycler_dashboards.adapter = adapter

        observe(viewModel.getDashBoardItems()) {
            adapter.submitList(it)
        }
    }
}
