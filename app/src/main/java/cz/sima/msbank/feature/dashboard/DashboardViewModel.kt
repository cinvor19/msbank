package cz.sima.msbank.feature.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.customview.loadingview.Error
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.Normal
import cz.sima.msbank.feature.dashboard.model.DashBoardItem

class DashboardViewModel(
    private val dashBoardRepository: DashBoardRepository
) : BaseViewModel() {

    private val dashBoardItems: MutableLiveData<List<DashBoardItem>> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Loading)

    fun getDashBoardItems(): LiveData<List<DashBoardItem>> = dashBoardItems
    fun getLoadingState(): LiveData<LoadingState> = loadingState

    init {
        fetchDashboard()
        fetchTransactions()
    }

    fun fetchDashboard() {

        loadingState.value = Loading
        subscribe(dashBoardRepository.fetchDashBoard(),
            {
                loadingState.value = Normal
                dashBoardItems.value = it
            },
            {
                loadingState.value = Error(it)
            })
    }

    fun fetchTransactions() {
        subscribe(dashBoardRepository.fetchTransactions("24"), {
            Log.d("Transaction", it.joinToString(","))
        })
    }

    fun onFabButtonClick() {
        fetchDashboard()
        // navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }
}
