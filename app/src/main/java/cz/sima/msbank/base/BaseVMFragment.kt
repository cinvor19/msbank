package cz.sima.msbank.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import cz.sima.msbank.MainActivity
import cz.sima.msbank.R
import cz.sima.msbank.event.BackEvent
import cz.sima.msbank.event.LiveEvent
import cz.sima.msbank.event.NavigationEvent
import cz.sima.msbank.utils.extensions.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

/**
 * Created by Michal Šíma on 05.06.2020.
 */
abstract class BaseVMFragment<B : ViewDataBinding, VM : BaseViewModel>(
    viewModelClass: KClass<VM>
) : Fragment() {

    abstract fun getLayoutId(): Int

    open fun hasBottomNav() = false

    protected lateinit var binding: B
    open val viewModel: VM by viewModel(viewModelClass)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.lifecycle, this)
        binding.setVariable(BR.vm, viewModel)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        subscribe(NavigationEvent::class) {
            navController().navigate(it)
        }
        subscribe(BackEvent::class) {
            navController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as? MainActivity)?.changeBottomNavVisibility(hasBottomNav())
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected fun <T : LiveEvent> subscribe(eventClass: KClass<T>, eventObserver: (T) -> Unit) {
        viewModel.subscribe(this, eventClass, Observer(eventObserver))
    }

    protected fun navController(): NavController {
        return NavHostFragment.findNavController(this)
    }

    protected fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null
    ) {
        navController().navigate(resId, args, navOptions)
    }

    protected fun navigate(directions: NavDirections, navOptions: NavOptions? = null) {
        navController().navigate(directions, navOptions)
    }

    protected fun showConfirmSnackBar(@StringRes stringRes: Int, action: (() -> Unit)? = null) {
        return showConfirmSnackBar(getString(stringRes), action)
    }

    protected fun showConfirmSnackBar(message: String, action: (() -> Unit)? = null) {
        showSnackBar(message, BaseTransientBottomBar.LENGTH_INDEFINITE, action)
    }

    protected fun showSnackBar(
        @StringRes stringRes: Int?,
        length: Int = BaseTransientBottomBar.LENGTH_LONG
    ) {
        showSnackBar(getString(requireNotNull(stringRes)))
    }

    protected fun showSnackBar(
        message: String,
        length: Int = BaseTransientBottomBar.LENGTH_LONG,
        action: (() -> Unit)? = null
    ) {
        view?.let { view ->
            val snack = Snackbar.make(view, message, length)
            if (length == BaseTransientBottomBar.LENGTH_INDEFINITE) {
                snack.setAction(R.string.confirm) {
                    snack.dismiss()
                    action?.invoke()
                }
            }
            snack.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
            snack.setActionTextColor(ContextCompat.getColor(view.context, R.color.white))
            snack.show()
        }
    }

    protected fun <T> observe(liveData: LiveData<T>?, observeFun: (T) -> Unit) {
        liveData?.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                observeFun.invoke(it)
            }
        })
    }

}