package cz.sima.msbank.utils.binding

import android.view.View
import androidx.databinding.BindingAdapter
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.LoadingView

/**
 * Created by Michal Šíma on 23.06.2020.
 */
@BindingAdapter("state")
fun LoadingView.setState(stateValue: LoadingState?) {
    stateValue?.let {
        if (stateValue != this.getCurrentState()) {
            this.showState(it)
        }
    }
}

@BindingAdapter("visibilityByState")
fun View.setVisibilityByState(stateValue: LoadingState?) {
    visibility = when (stateValue) {
        Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("onReloadClick")
fun LoadingView.setReloadClickAction(action: (() -> Unit)?) {
    action?.let {
        this.setReloadClickAction(action)
    }
}
