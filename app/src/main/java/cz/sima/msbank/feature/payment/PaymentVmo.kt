package cz.sima.msbank.feature.payment

import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 * Created by Michal Šíma on 28.06.2020.
 */
class PaymentVmo {
    val fromAccount: MutableLiveData<String> = MutableLiveData()
    val toAccount: MutableLiveData<String> = MutableLiveData()
    val amount: MutableLiveData<String> = MutableLiveData()
    val date: MutableLiveData<Long> = MutableLiveData()
    val variableSymbol: MutableLiveData<String> = MutableLiveData()
    val specificSymbol: MutableLiveData<String> = MutableLiveData()
    val constantSymbol: MutableLiveData<String> = MutableLiveData()
    val messageForMe: MutableLiveData<String> = MutableLiveData()
    val messageForReceiver: MutableLiveData<String> = MutableLiveData()

    fun print() {
        listOf(
            fromAccount,
            toAccount,
            amount,
            date,
            variableSymbol,
            specificSymbol,
            constantSymbol,
            messageForMe,
            messageForReceiver
        ).map {
            Log.d("VMO", it.value.toString())
        }
    }
}
