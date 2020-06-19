package cz.sima.msbank.feature.cards

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseRecyclerAdapter
import cz.sima.msbank.base.DataBindingViewHolder

/**
 * Created by Michal Šíma on 12.06.2020.
 */
class CardAdapter(parentViewModel: CardsViewModel) :
    BaseRecyclerAdapter<Card>(parentViewModel, CardItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Card> {
        return CardViewHolder(getViewDataBinding(parent, R.layout.item_card))
    }

    inner class CardViewHolder(binding: ViewDataBinding) : DataBindingViewHolder<Card>(binding)
}
