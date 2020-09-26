package cz.sima.msbank.feature.settings

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentSplashBinding
import cz.sima.msbank.feature.dashboard.DashBoardAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment :
    BaseVMFragment<FragmentSplashBinding, SettingsViewModel>(SettingsViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_settings

    override fun hasBottomNav() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val adapter = SettingsAdapter(viewModel)
        recycler_settings.layoutManager = LinearLayoutManager(context)
        recycler_settings.adapter = adapter
        recycler_settings.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        observe(viewModel.getSettingsItems()) {
            adapter.submitList(it)
        }
    }
}
