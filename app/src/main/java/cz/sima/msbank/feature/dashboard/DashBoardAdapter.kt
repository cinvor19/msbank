package cz.sima.msbank.feature.dashboard

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseRecyclerAdapter
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.base.DataBindingViewHolder
import cz.sima.msbank.feature.dashboard.model.DashBoardAccount
import cz.sima.msbank.feature.dashboard.model.DashBoardAnnouncement
import cz.sima.msbank.feature.dashboard.model.DashBoardDiffUtil
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import cz.sima.msbank.feature.dashboard.model.DashBoardItemType
import cz.sima.msbank.feature.dashboard.model.DashBoardPromo
import cz.sima.msbank.feature.dashboard.model.DashBoardSpace
import kotlinx.android.synthetic.main.item_dashboard_account.view.*

/**
 * Created by Michal Šíma on 20.06.2020.
 */
class DashBoardAdapter(dashboardViewModel: DashboardViewModel) :
    BaseRecyclerAdapter<DashBoardItem>(
        dashboardViewModel,
        DashBoardDiffUtil
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<DashBoardItem> {
        return when (DashBoardItemType.fromInt(viewType)) {
            DashBoardItemType.ACCOUNT -> AccountViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_dashboard_account
                )
            )
            DashBoardItemType.PROMO -> PromoViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_dashboard_promo
                )
            )
            DashBoardItemType.ANNOUNCEMENT -> AnnouncementViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_dashboard_announcement
                )
            )
            DashBoardItemType.SPACE -> SpaceViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_dashboard_space
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getItemType().viewType
    }

    inner class AccountViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardAccount>(binding) {

        override fun bindTo(data: DashBoardAccount, parentViewModel: BaseViewModel) {
            super.bindTo(data, parentViewModel)
            itemView.recycler_account_transactions
            initRecycler(data, parentViewModel as DashboardViewModel)

        }

        private fun initRecycler(data: DashBoardAccount, parentViewModel: DashboardViewModel) {
            val adapter = DashBoardAccountTransactionAdapter(parentViewModel)
            itemView.recycler_account_transactions.layoutManager = LinearLayoutManager(context)
            itemView.recycler_account_transactions.adapter = adapter
            itemView.recycler_account_transactions.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter.submitList(data.transactions.value)
        }
    }

    inner class AnnouncementViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardAnnouncement>(
            binding
        )

    inner class PromoViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardPromo>(
            binding
        )

    inner class SpaceViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardSpace>(
            binding
        )
}
