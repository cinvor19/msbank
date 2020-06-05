package cz.sima.msbank.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.sima.msbank.MainActivity

/**
 * Created by Michal Šíma on 05.06.2020.
 */
abstract class BaseFragment : Fragment() {

    abstract fun getLayoutId(): Int

    open fun hasBottomNav() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.changeBottomNavVisibility(hasBottomNav())
    }
}