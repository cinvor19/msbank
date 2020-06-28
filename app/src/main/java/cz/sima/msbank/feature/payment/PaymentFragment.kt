package cz.sima.msbank.feature.payment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentPaymentBinding
import cz.sima.msbank.shared.BankAccount
import cz.sima.msbank.utils.Validations
import cz.sima.msbank.utils.extensions.validateLength
import cz.sima.msbank.utils.extensions.validateNotEmpty
import cz.sima.msbank.utils.extensions.validateRule
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.fragment_payment.*

/**
 * Created by Michal Šíma on 20.06.2020.
 */
class PaymentFragment :
    BaseVMFragment<FragmentPaymentBinding, PaymentViewModel>(PaymentViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initValidations()
        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, items)
        (til_from_account.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initObservers() {
        observe(viewModel.getShowDatePicker()) {
            showDatePicker(it)
        }
    }

    private fun initValidations() {

        val validationsList: List<Flowable<Boolean>?> = listOf(
            til_to_account.validateRule({ Validations.isBankAccountValid(BankAccount.fromString(it.toString()))}, "Error"),
            til_amount.validateRule({ it.toString() == "ahoj" }, "Error")
        )

        viewModel.startValidation(validationsList)
    }

    private fun showDatePicker(selectedDate: Long) {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(selectedDate)
            .setTitleText(R.string.payment_date)
            .build()

        picker.addOnPositiveButtonClickListener {
            viewModel.setPaymentDate(it)
        }

        picker.showNow(parentFragmentManager, picker.tag)

    }
}
