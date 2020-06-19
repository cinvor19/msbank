package cz.sima.msbank.feature.pin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.event.AnySingleLiveEvent
import cz.sima.msbank.utils.Constants
import cz.sima.msbank.utils.extensions.cutLastChar

/**
 * Created by Michal Šíma on 13.06.2020.
 */
class PinViewModel : BaseViewModel() {

    val pin: MutableLiveData<String> = MutableLiveData("")

    private val showPinError: AnySingleLiveEvent = AnySingleLiveEvent()

    fun getShowPinError(): LiveData<Any> = showPinError
    fun onKeyboardClicked(view: View) {
        val tmpPin = pin.value
        with(view as? MaterialButton) {

            if (tmpPin?.length == Constants.PIN_LENGTH) {
                return
            }
            pin.value = tmpPin + this?.text
        }

        checkPinCompleteness()
    }

    private fun checkPinCompleteness() {
        if (pin.value?.length == Constants.PIN_LENGTH) {
            checkPinValid()
        }
    }

    private fun checkPinValid() {
        if (pin.value == "1234") {
            navigate(R.id.action_pinFragment_to_navigation_dashboard)
        } else {
            resetViews()
            showPinError.publish()
        }
    }

    fun onEraseClicked() {
        val tmpPin = pin.value
        pin.value = tmpPin?.cutLastChar()
    }

    fun onBiometricsClicked() {
        showTodoToast.publish()
    }

    private fun resetViews() {
        pin.value = ""
    }
}
