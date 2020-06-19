package cz.sima.msbank.feature.payment

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentPaymentBinding

/**
 * Created by Michal Šíma on 20.06.2020.
 */
class PaymentFragment :
    BaseVMFragment<FragmentPaymentBinding, PaymentViewModel>(PaymentViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_payment
}
