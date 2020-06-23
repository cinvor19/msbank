package cz.sima.msbank.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.customview.loadingview.Error
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.Normal
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import java.util.concurrent.TimeUnit

class DashboardViewModel(private val dashBoardRepository: DashBoardRepository) : BaseViewModel() {

    private val dashBoardItems: MutableLiveData<List<DashBoardItem>> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Loading)

    fun getDashBoardItems(): LiveData<List<DashBoardItem>> = dashBoardItems
    fun getLoadingState(): LiveData<LoadingState> = loadingState

    fun fetchData() {

        loadingState.value = Loading
        subscribe(dashBoardRepository.fetchDashBoard().delay(3000, TimeUnit.MILLISECONDS),
            {
                loadingState.value = Normal
                dashBoardItems.value = it
            },
            {
                loadingState.value = Error(it)
            })
    }

    fun onFabButtonClick() {
        fetchData()
        // navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }
}
