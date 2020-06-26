package cz.sima.msbank.feature.dashboard

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseRecyclerAdapter
import cz.sima.msbank.base.DataBindingViewHolder
import cz.sima.msbank.shared.Transaction
import cz.sima.msbank.shared.TransactionDiffUtil

/**
 * Created by Michal Šíma on 26.06.2020.
 */
class DashBoardAccountTransactionAdapter(dashboardViewModel: DashboardViewModel) :
    BaseRecyclerAdapter<Transaction>(dashboardViewModel, TransactionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DataBindingViewHolder<Transaction> {
        return DashBoardAccountTransactionViewHolder(
            getViewDataBinding(
                parent,
                R.layout.item_account_transaction
            )
        )

    }


    inner class DashBoardAccountTransactionViewHolder(binding: ViewDataBinding) :
        DataBindingViewHolder<Transaction>(
            binding
        )
}
