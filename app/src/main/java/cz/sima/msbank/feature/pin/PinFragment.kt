package cz.sima.msbank.feature.pin

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentPinBinding

/**
 * Created by Michal Šíma on 13.06.2020.
 */
class PinFragment : BaseVMFragment<FragmentPinBinding, PinViewModel>(PinViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_pin

    override fun hasBottomNav() = false

}