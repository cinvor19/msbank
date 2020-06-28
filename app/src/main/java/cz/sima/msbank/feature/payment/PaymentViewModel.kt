package cz.sima.msbank.feature.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.event.SingleLiveEvent
import io.reactivex.Flowable

/**
 * Created by Michal Šíma on 20.06.2020.
 */
class PaymentViewModel(val paymentVmo: PaymentVmo) : BaseViewModel() {

    private val showDatePicker: SingleLiveEvent<Long> = SingleLiveEvent()
    private val isValid: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getShowDatePicker(): LiveData<Long> = showDatePicker
    fun getIsValid(): LiveData<Boolean> = isValid

    fun onPaymentDateClicked() {
        showDatePicker.value = paymentVmo.date.value ?: System.currentTimeMillis()
    }

    fun onPayClicked() {
        paymentVmo.print()
    }

    fun setPaymentDate(paymentDate: Long) {
        paymentVmo.date.value = paymentDate
    }

    fun startValidation(flowables: List<Flowable<Boolean>?>) {
            Flowable.combineLatest(flowables) { t -> !(t.contains(false)) }
                .subscribe { isValid.value = it }
    }
}
