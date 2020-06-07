package cz.sima.msbank.utils.bindings

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.sima.msbank.base.*

/**
 * Created by Michal Šíma on 07.06.2020.
 */
@BindingAdapter(
    value = ["viewModel", "items", "layoutId", "layoutStrategy", "orientation", "lifecycle", "parentItem"],
    requireAll = false
)
fun <T> bindItems(
    view: RecyclerView,
    vm: BaseViewModel?,
    items: ObservableArrayList<T>,
    layoutId: Int?,
    layoutStrategy: RecyclerLayoutStrategy?,
    orientation: Int?,
    lifecycleOwner: LifecycleOwner?,
    parentItem: Any?
) {
    if (view.adapter == null) {
        if (view.layoutManager == null) {
            view.layoutManager = object : LinearLayoutManager(
                view.context, orientation
                    ?: RecyclerView.VERTICAL, false
            ) {
                override fun supportsPredictiveItemAnimations(): Boolean {
                    return false
                }
            }
        }
        if (layoutStrategy == null) {
            if (layoutId != null) {
                view.adapter = SingleTypeRecyclerAdapter(items, vm, layoutId)
            }
        } else {
            view.adapter =
                StrategyRecyclerAdapter(items as ObservableArrayList<Any>, layoutStrategy, vm)
        }
    } else {
        (view.adapter as BaseRecyclerAdapter<T>).setItems(items)
    }
    (view.adapter as BaseRecyclerAdapter<*>).lifecycleOwner = lifecycleOwner
    (view.adapter as BaseRecyclerAdapter<*>).parentItem = parentItem

}