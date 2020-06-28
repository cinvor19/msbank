package cz.sima.msbank.feature.payment

import androidx.lifecycle.LiveData
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.event.SingleLiveEvent

/**
 * Created by Michal Šíma on 20.06.2020.
 */
class PaymentViewModel(val paymentVmo: PaymentVmo) : BaseViewModel() {

    private val showDatePicker: SingleLiveEvent<Long> = SingleLiveEvent()

    fun getShowDatePicker(): LiveData<Long> = showDatePicker

    fun onPaymentDateClicked() {
        showDatePicker.value = paymentVmo.date.value ?: System.currentTimeMillis()
    }

    fun onPayClicked() {
        paymentVmo.print()
    }

    fun setPaymentDate(paymentDate: Long) {
        paymentVmo.date.value = paymentDate
    }
}
