package cz.sima.msbank.feature.payment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentPaymentBinding
import kotlinx.android.synthetic.main.fragment_payment.*

/**
 * Created by Michal Šíma on 20.06.2020.
 */
class PaymentFragment :
    BaseVMFragment<FragmentPaymentBinding, PaymentViewModel>(PaymentViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, items)
        (til_from_account.editText as? AutoCompleteTextView)?.setAdapter(adapter)

    }
}
