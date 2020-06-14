package cz.sima.msbank.feature.pin

import android.os.Bundle
import android.view.View
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentPinBinding

/**
 * Created by Michal Šíma on 13.06.2020.
 */
class PinFragment : BaseVMFragment<FragmentPinBinding, PinViewModel>(PinViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_pin

    override fun hasBottomNav() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    fun initObservers() {
        observe(viewModel.getShowPinError()) {
            showSnackBar(R.string.pin_invalid)
        }
    }

}