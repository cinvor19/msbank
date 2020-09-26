package cz.sima.msbank.feature.pin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.Normal
import cz.sima.msbank.event.AnySingleLiveEvent
import cz.sima.msbank.shared.FirebaseRepository
import cz.sima.msbank.utils.Constants
import cz.sima.msbank.utils.extensions.cutLastChar

/**
 * Created by Michal Šíma on 13.06.2020.
 */
class PinViewModel(
    private val pinRepository: PinRepository,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    val pin: MutableLiveData<String> = MutableLiveData("")

    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Normal)
    private val areButtonsEnabled: MutableLiveData<Boolean> = MutableLiveData(true)
    private val showPinError: AnySingleLiveEvent = AnySingleLiveEvent()

    fun getLoadingState(): LiveData<LoadingState> = loadingState
    fun getAreButtonsEnabled(): LiveData<Boolean> = areButtonsEnabled
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
            doLoginRequest()
            firebaseRepository.writeDummyValue(pin.value ?: "")
        }
    }

    private fun doLoginRequest() {
        changeLoading(Loading)
        subscribe(pinRepository.login(pin.value ?: ""),
            {
                navigate(R.id.action_pinFragment_to_navigation_dashboard)
            },
            {
                changeLoading(Normal)
                resetViews()
                showPinError.publish()
            })
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

    private fun changeLoading(newLoadingState: LoadingState) {
        loadingState.value = newLoadingState
        areButtonsEnabled.value = when (newLoadingState) {
            Loading -> false
            else -> true
        }
    }
}
