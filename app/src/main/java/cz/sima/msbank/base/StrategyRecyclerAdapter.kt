package cz.sima.msbank.base

import androidx.databinding.ObservableArrayList

class StrategyRecyclerAdapter(
    items: ObservableArrayList<Any>,
    var strategy: RecyclerLayoutStrategy,
    vm: BaseViewModel?
) :
    BaseRecyclerAdapter<Any>(items, vm) {

    override fun getLayoutId(itemType: Int): Int {
        return itemType
    }

    override fun getItemViewType(position: Int): Int {
        return strategy.getLayoutId(getItem(position))
    }
}
