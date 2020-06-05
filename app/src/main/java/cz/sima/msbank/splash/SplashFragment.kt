package cz.sima.msbank.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import cz.sima.msbank.R
import cz.sima.msbank.R.layout
import cz.sima.msbank.base.BaseFragment

/**
 * Created by Michal Šíma on 05.06.2020.
 */
class SplashFragment : BaseFragment() {

    override fun getLayoutId() = layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<AppCompatButton>(R.id.btn_continue).setOnClickListener {
            findNavController().navigate(R.id.action_fragment_splash_to_navigation_dashboard)
        }
    }

}