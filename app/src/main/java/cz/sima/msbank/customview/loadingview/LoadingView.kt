package cz.sima.msbank.customview.loadingview

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cz.sima.msbank.R
import retrofit2.HttpException
import java.io.IOException
import java.io.Serializable

/**
 * Created by Michal Šíma on 14.06.2020.
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var lastState: LoadingState? = null
    private var isInitialStateConsumed = false

    private var loadingLayoutId = R.layout.loading_view_loading_default
    private var errorLayoutId = R.layout.loading_view_error_defaut
    private var emptyLayoutId = R.layout.loading_view_empty_default

    private var buttonErrorReloadId = R.id.buttonLoadingViewErrorReload

    private var normalView: Map<View?, Int?>? = null // save visibility state for each view
    private val loadingView: View
    private val errorView: View
    private val emptyView: View

    private val textErrorMessage: TextView?
    private val buttonErrorReload: View?

    private var throwableToErrorMapping: ((Throwable) -> String)? = {
        val stringResId = when {
            it is HttpException && it.code() >= 500 -> R.string.loading_view_default_error_server_error
            it is HttpException && it.code() < 500 -> R.string.loading_view_default_error_client_error
            it is IOException -> R.string.loading_view_default_error_no_internet
            else -> R.string.loading_view_default_error
        }

        context.getString(stringResId)
    }

    init {
        if (id == View.NO_ID) {
            throw IllegalStateException("LoadingView should have unique id set in xml")
        }

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.LoadingView, 0, 0)

            loadingLayoutId = typedArray.getResourceId(R.styleable.LoadingView_layout_loading, R.layout.loading_view_loading_default)
            errorLayoutId = typedArray.getResourceId(R.styleable.LoadingView_layout_error, R.layout.loading_view_error_defaut)
            emptyLayoutId = typedArray.getResourceId(R.styleable.LoadingView_layout_empty, R.layout.loading_view_empty_default)
            buttonErrorReloadId = typedArray.getResourceId(R.styleable.LoadingView_layout_error_refresh_button_id, R.id.buttonLoadingViewErrorReload)

            typedArray.recycle()
        }
        loadingView = LayoutInflater.from(context).inflate(loadingLayoutId, this, false)
        errorView = LayoutInflater.from(context).inflate(errorLayoutId, this, false)
        emptyView = LayoutInflater.from(context).inflate(emptyLayoutId, this, false)

        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE

        textErrorMessage = errorView.findViewById(R.id.textLoadingViewErrorMessage)
        buttonErrorReload = errorView.findViewById(buttonErrorReloadId)
    }

    private fun initializeViews() {
        if (normalView.isNullOrEmpty()) {
            normalView = getAllChildren()

            addView(loadingView)
            addView(errorView)
            addView(emptyView)
        }
    }

    private fun getAllChildren(): Map<View?, Int?> {
        val allChildren = HashMap<View?, Int?>()

        for (i in 0..childCount) {
            val view = getChildAt(i)
            allChildren[view] = view?.visibility
        }

        return allChildren
    }

    fun setReloadClickAction(action: () -> Unit) {
        buttonErrorReload?.setOnClickListener { action.invoke() }
    }

    fun showState(loadingState: LoadingState) {

        initializeViews()

        when (loadingState) {
            is Normal -> showStateLayout(normalView)
            is Loading -> showStateLayout(loadingView)
            is Empty -> showStateLayout(emptyView)
            is Error -> {
                val errorMessage: String? = throwableToErrorMapping?.invoke(loadingState.throwable)
                showStateLayout(errorView, errorMessage)
            }
        }

        lastState = loadingState
    }

    private fun showStateLayout(mapOfViews: Map<View?, Int?>?) {
        hideAllViews()
        mapOfViews?.forEach { (view, lastVisibilityState) ->
            lastVisibilityState?.let {
                view?.visibility = it
            }
        }
    }

    private fun showStateLayout(stateView: View, errorMessage: String? = null) {
        hideAllViews()
        stateView.visibility = View.VISIBLE

        errorMessage?.let {
            textErrorMessage?.text = errorMessage
        }
    }

    private fun checkIfStateHasToBeSaved(mapOfViews: Map<View?, Int?>?): Boolean {
        // if all views are hidden -> don't save the state
        // We may have flow like this -> loading, error, loading, normal ->
        // -> in all states we are first hiding all views which means we have to save the state of normal view right before it is changed to GONE.
        // In the above case, the state will be lastly saved right before error state is executed and will restore on normal state
        mapOfViews?.forEach { (_, currentVisibilityState) ->
            if (lastState == Normal &&
                currentVisibilityState != null &&
                currentVisibilityState != View.GONE) {
                return true
            }
        }

        return false
    }

    private fun hideAllViews() {
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE

        val mapOfViews = getAllChildren()
        if (checkIfStateHasToBeSaved(mapOfViews)) {
            normalView = mapOfViews // saves last visibility state for each view before hiding it
        }

        mapOfViews.forEach { (view, _) ->
            view?.visibility = View.GONE
        }
    }

    fun getCurrentState() = lastState

    fun updateVisibilityState(source: View, newVisibilityState: Int) {
        if (lastState == Normal) {
            return
        }

        val newNormalView = HashMap<View?, Int?>()

        normalView?.forEach { (view, lastVisibilityState) ->
            val visibilityState = if (view == source) newVisibilityState else lastVisibilityState
            newNormalView[view] = visibilityState
        }

        normalView = newNormalView
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState: Parcelable? = super.onSaveInstanceState()

        superState?.let {
            val savedState =
                SavedState(
                    superState
                )
            savedState.loadingState = lastState
            savedState.isInitialStateConsumed = isInitialStateConsumed

            return savedState
        }

        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState: SavedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)

        savedState.loadingState?.let {
            lastState = it
            showState(it)
        }
        isInitialStateConsumed = savedState.isInitialStateConsumed
    }

    companion object {
        class SavedState : BaseSavedState {

            var loadingState: LoadingState? = null
            var isInitialStateConsumed = false

            constructor(superState: Parcelable) : super(superState)

            private constructor(parcel: Parcel) : super(parcel) {
                this.loadingState = parcel.readSerializable() as LoadingState
                this.isInitialStateConsumed = parcel.readInt() == 1
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                super.writeToParcel(parcel, flags)
                loadingState?.let {
                    parcel.writeSerializable(it as Serializable)
                    parcel.writeInt(if (isInitialStateConsumed) 1 else 0)
                }
            }

            companion object CREATOR : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(
                        parcel
                    )
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
