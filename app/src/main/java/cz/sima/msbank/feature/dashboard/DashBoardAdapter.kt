package cz.sima.msbank.feature.dashboard

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseRecyclerAdapter
import cz.sima.msbank.base.DataBindingViewHolder
import cz.sima.msbank.feature.dashboard.model.DashBoardAccount
import cz.sima.msbank.feature.dashboard.model.DashBoardAnnouncement
import cz.sima.msbank.feature.dashboard.model.DashBoardCreditCard
import cz.sima.msbank.feature.dashboard.model.DashBoardDiffUtil
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import cz.sima.msbank.feature.dashboard.model.DashBoardItemType
import cz.sima.msbank.feature.dashboard.model.DashBoardPromo

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
            DashBoardItemType.CREDIT -> CreditCardViewHolder(
                getViewDataBinding(
                    parent,
                    R.layout.item_dashboard_credit_card
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
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getItemType().viewType
    }

    inner class AccountViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardAccount>(
            binding
        )

    inner class AnnouncementViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardAnnouncement>(
            binding
        )

    inner class CreditCardViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardCreditCard>(
            binding
        )

    inner class PromoViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<DashBoardPromo>(
            binding
        )
}
