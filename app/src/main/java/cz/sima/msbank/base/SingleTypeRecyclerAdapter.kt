package cz.sima.msbank.base

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableArrayList


/**
 * Created by Michal Šíma on 07.06.2020.
 */
class SingleTypeRecyclerAdapter<T> : BaseRecyclerAdapter<T> {


    @LayoutRes
    private var layoutId: Int = 0

    constructor(
        items: ObservableArrayList<T>,
        viewModel: BaseViewModel?,
        itemLaoyutId: Int
    ) : super(items, viewModel) {
        this.layoutId = itemLaoyutId
    }

    constructor(items: ObservableArrayList<T>, itemLayoutId: Int) : super(items) {
        this.layoutId = itemLayoutId
    }

    override fun getLayoutId(itemType: Int): Int {
        return layoutId
    }
}
