package cz.sima.msbank.feature.splash

import cz.sima.msbank.R.layout
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentSplashBinding

/**
 * Created by Michal Šíma on 05.06.2020.
 */
class SplashFragment :
    BaseVMFragment<FragmentSplashBinding, SplashScreenViewModel>(SplashScreenViewModel::class) {

    override fun getLayoutId() = layout.fragment_splash
}
