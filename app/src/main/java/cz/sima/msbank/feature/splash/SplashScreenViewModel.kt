package cz.sima.msbank.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.Normal
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

/**
 * Created by Michal Šíma on 05.06.2020.
 */
class SplashScreenViewModel : BaseViewModel() {

    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Loading)

    fun getLoadingState(): LiveData<LoadingState> = loadingState

    init {
        subscribe(Completable.complete()
            .delay(1000, TimeUnit.MILLISECONDS), {
            loadingState.value = Normal
            navigate(R.id.action_navigation_splash_to_pinFragment)
        })
    }
}
