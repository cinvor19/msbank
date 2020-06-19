package cz.sima.msbank.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Michal Šíma on 11.06.2020.
 */
abstract class BaseRecyclerAdapter<T>(
    private val parentViewModel: BaseViewModel,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    var onItemClickAction: ((T) -> Unit)? = null
    var onItemLongClickAction: ((T) -> Boolean)? = null

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<T>

    protected fun getViewDataBinding(
        parent: ViewGroup,
        layoutId: Int
    ): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
    }

    override fun onBindViewHolder(viewHolder: DataBindingViewHolder<T>, position: Int) {
        val data: T = getItem(position)

        viewHolder.itemView.setOnClickListener { onItemClickAction?.invoke(data) }

        viewHolder.itemView.setOnLongClickListener {
            // return true if you want only long click event to be consumed
            // return false if you want other click events to be consumed after long click event
            return@setOnLongClickListener onItemLongClickAction?.invoke(data) ?: false
        }

        viewHolder.bindTo(data, parentViewModel)
    }

    override fun submitList(list: List<T>?) {
        list?.let {
            super.submitList(ArrayList<T>(it))
        }
    }
}

abstract class DataBindingViewHolder<out T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    protected val context: Context by lazy { itemView.context }

    open fun bindTo(data: @UnsafeVariance T, parentViewModel: BaseViewModel) {
        binding.setVariable(getItemBindingId(), data)
        binding.setVariable(BR.vm, parentViewModel)
        binding.executePendingBindings()
    }

    protected open fun getItemBindingId(): Int = BR.item

    protected fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    protected fun getString(@StringRes stringResId: Int, vararg formatArgs: Any?): String {
        return context.getString(stringResId, *formatArgs)
    }

    protected fun getQuantityString(
        @PluralsRes pluralsResId: Int,
        quantity: Int,
        vararg formatArgs: Any?
    ): String {
        return context.resources.getQuantityString(pluralsResId, quantity, *formatArgs)
    }

    protected fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }

    protected fun getDimen(@DimenRes dimenResId: Int): Float {
        return context.resources.getDimension(dimenResId)
    }
}
